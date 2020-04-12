package com.example.listener;

import com.example.calculator.Calculator;
import com.example.common.Constants;
import com.example.common.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqListener implements OperationListener {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqListener.class);

    @Autowired
    private Calculator calculator;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public String receive(@Payload Operation request) {
        MDC.put(Constants.REQUEST_ID_KEY, request.getRequestId());

        LOG.info("Rabbit listener in calculator module received request to perform operation {} with a = {} and b = {}",
                request.getOperationType().toString(),
                request.getA(),
                request.getB());

        switch (request.getOperationType()) {
            case SUM:
                return calculator.sum(request.getA(), request.getB());
            case SUB:
                return calculator.subtraction(request.getA(), request.getB());
            case MUL:
                return calculator.multiplication(request.getA(), request.getB());
            case DIV:
                return calculator.division(request.getA(), request.getB());
        }

        return null;
    }
}