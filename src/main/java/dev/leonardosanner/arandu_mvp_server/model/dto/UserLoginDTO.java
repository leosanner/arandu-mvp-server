package dev.leonardosanner.arandu_mvp_server.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDTO {

    @Email
    private String email;

    @Length(min = 10)
    private String password;
}
