package dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
