package com.starchee.simplecalculator.domain;

public class InvalidMathExpressionException extends RuntimeException {
    public InvalidMathExpressionException(String message) {
        super(message);
    }
}
