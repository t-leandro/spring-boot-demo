package com.example.common;


public enum OperationType {
    SUM,
    SUB,
    MUL,
    DIV;

    public static OperationType from(String name){
        try {
           return OperationType.valueOf(name.toUpperCase());
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
}
