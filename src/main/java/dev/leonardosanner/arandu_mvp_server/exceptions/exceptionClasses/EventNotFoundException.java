package dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
