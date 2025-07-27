package dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
