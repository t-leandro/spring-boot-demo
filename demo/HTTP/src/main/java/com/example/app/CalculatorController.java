package com.example.app;

import com.example.common.Constants;
import com.example.common.Operation;
import com.example.common.OperationType;
import com.example.senders.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Class that handles HTTP requests
 */
@RestController
public class CalculatorController {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private Executor executor;

    @Autowired
    private Sender sender;

    @RequestMapping(value = "/calculator/{operator}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBodyEmitter> calculator(
            @PathVariable("operator") String operator,
            @RequestParam(value = "a", defaultValue = "0") String a,
            @RequestParam(value = "b", defaultValue = "0") String b) {
        LOG.info("Received {} request with operands a={} and b={}", operator, a, b);

        // Create response
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Get operation type and return 404 Not Found if it is not valid
        String requestId = MDC.get(Constants.REQUEST_ID_KEY);
        OperationType operationType =  OperationType.from(operator);

        HttpStatus validationStatus = validateRequest(operationType, a, b);

        if (validationStatus != null) {
            return ResponseEntity.status(validationStatus).body(null);
        }

        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        // Get result from Calculator module asynchronously
        executor.execute(() -> {
            try {
                // MDC context is stored by thread
                // Copy MDC context of http request to this thread
                MDC.setContextMap(contextMap);

                Operation request = new Operation(operationType, a, b, requestId);
                String result = sender.send(request);

                if (result == null){
                    throw (new Exception("Calculator module could not perform the operation"));
                }

                emitter.send(new OperationResponse(result));
                emitter.complete();
            }
            catch (Exception e) {
               LOG.error("Could not obtain valid result", e);
               emitter.completeWithError(e);
            }
            finally {
                MDC.clear();
            }
        });

        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static HttpStatus validateRequest (OperationType operationType, String a, String b) {
        if (operationType == null) {
            LOG.warn("Returning 404 Not found to invalid operator");
            return HttpStatus.NOT_FOUND;
        }

        if(!isNumeric(a) || !isNumeric(b)){
            LOG.warn("Returning 40O Bad request to invalid operand(s): a={} and b={}", a ,b);
            return HttpStatus.BAD_REQUEST;
        }

        return null;
    }
}
