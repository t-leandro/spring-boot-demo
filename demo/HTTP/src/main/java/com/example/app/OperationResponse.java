package com.example.app;

/**
 * POJO used to generate HTTP response JSON.
 */
public class OperationResponse {
    private String result;

    public OperationResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
