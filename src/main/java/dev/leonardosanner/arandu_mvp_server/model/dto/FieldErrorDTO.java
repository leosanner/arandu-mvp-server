package dev.leonardosanner.arandu_mvp_server.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldErrorDTO {

    private String fieldName;
    private String message;
}
