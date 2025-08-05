package dev.leonardosanner.arandu_mvp_server.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserJWTResponseDTO {

    private String message;
    private String token;
    private boolean success;
}
