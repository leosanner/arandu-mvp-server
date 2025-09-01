package dev.leonardosanner.arandu_mvp_server.exceptions;


import dev.leonardosanner.arandu_mvp_server.model.dto.FieldErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class InvalidFieldsHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorDTO>> argumentNotValidHandler(MethodArgumentNotValidException e) {

        List<FieldErrorDTO> errorDTO = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err ->
                errorDTO.add(FieldErrorDTO.builder()
                        .fieldName(err.getField())
                        .message(err.getDefaultMessage())
                        .build()));

        return ResponseEntity.badRequest().body(errorDTO);
    }
}
