package com.example.common;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Operation implements Serializable {
    private  OperationType operationType;
    private  String a;
    private  String b;
    private  String requestId;

    public Operation(){

    }

    public Operation(@NotNull OperationType operationType, @NotNull String a, @NotNull String b, @NotNull String requestId) {
        Objects.requireNonNull(operationType, "operationType must not be null!");

        this.operationType = operationType;
        this.a = a;
        this.b = b;
        this.requestId = requestId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
