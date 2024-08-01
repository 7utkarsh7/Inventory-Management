package com.example.inventory.exceptions;

public class InvalidProductQuantityException extends RuntimeException {
    public InvalidProductQuantityException(String message) {
        super(message);
    }

    public InvalidProductQuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
