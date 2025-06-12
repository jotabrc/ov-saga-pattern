package io.github.jotabrc.ov_saga.handler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
