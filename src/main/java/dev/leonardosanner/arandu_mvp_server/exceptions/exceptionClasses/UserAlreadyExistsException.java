package dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
