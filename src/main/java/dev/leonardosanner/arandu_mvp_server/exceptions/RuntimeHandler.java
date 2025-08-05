package dev.leonardosanner.arandu_mvp_server.exceptions;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.InvalidUserCredentialsException;
import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserAlreadyExistsException;
import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RuntimeHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(
                this.buildErrorResponse(e.getMessage())
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExists(UserAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(
                this.buildErrorResponse(e.getMessage())
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFounded(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(
                this.buildErrorResponse(e.getMessage())
        );
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<Object> userNotFounded(InvalidUserCredentialsException e) {
        return ResponseEntity.badRequest().body(
                this.buildErrorResponse(e.getMessage())
        );
    }

    private ResponseEntity<Object> buildErrorResponse(String message) {
        return ResponseEntity.badRequest().body(
                BasicResponseDTO.builder()
                        .success(false)
                        .message(message)
                        .build()
        );
    }
}
