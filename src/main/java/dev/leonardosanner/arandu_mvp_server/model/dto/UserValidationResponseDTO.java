package dev.leonardosanner.arandu_mvp_server.model.dto;

import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserValidationResponseDTO {

    private String message;
    private Boolean success;
    private UserEntity userEntity;
}
