package org.example;

public class MissingValueException extends IllegalArgumentException {
    public MissingValueException(String message) {
        super(message);
    }
}
