package com.example.listener;

import com.example.common.Operation;

/**
 * Interface used to receive events from other modules
 * The implementation to be used depends on how requests are received (examples: HTTP, rabbitMQ, ...)
 */
public interface OperationListener {
    /**
     *
     * @param request Operation request. It has the operation value according to OperationTypes enum, the operands
     * and the request id
     * @return It returns the String representation of the result of the operation
     */
    String receive(Operation request);
}
