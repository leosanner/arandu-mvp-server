package dev.leonardosanner.arandu_mvp_server.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateUserDTO {

    private String username;

    @Length(min = 10)
    private String password;

    @Email
    private String email;

    private String firstName;

    private String lastName;
}
