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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

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

    @GetMapping("/calculator/{operator}")
    public ResponseEntity<ResponseBodyEmitter> calculator(
            @PathVariable("operator") String operator,
            @RequestParam(value = "a", defaultValue = "0") String a,
            @RequestParam(value = "b", defaultValue = "0") String b) {
        LOG.info("Received {} request with operands a={} and b={}", operator, a, b);

        // Create response
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Get operation type and return 400 Not Found if it is not valid
        String requestId = MDC.get(Constants.REQUEST_ID_KEY);
        String requestedOperationType = operator.toUpperCase();
        OperationType operationType =  OperationType.from(requestedOperationType);

        if (operationType == null) {
            LOG.warn("Returning 404 to invalid operator {}", operator);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Get result from Calculator module
        executor.execute(() -> {

            try {
                Operation request = new Operation(operationType, a, b, requestId);
                String result = sender.send(request);
                emitter.send(result);
            }
            catch (Exception e) {
               LOG.error("Could not obtain valid result", e);
               emitter.completeWithError(e);
            }

            emitter.complete();
        });

        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }
}
