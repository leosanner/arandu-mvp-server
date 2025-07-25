package dev.leonardosanner.arandu_mvp_server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
