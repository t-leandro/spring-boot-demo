package com.example.senders;

/**
 * Interface used to send messages to calculator module
 * Can be implemented with rabbit mq
 */
public interface Sender {
    /**
     *
     * @param request Message to send to module
     * @param <T> It is the type of the message
     * @return Response returned by calculator module
     */
    <T> String send(T request);
}
