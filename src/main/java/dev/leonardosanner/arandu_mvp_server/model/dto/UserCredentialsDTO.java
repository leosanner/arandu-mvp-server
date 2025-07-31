package dev.leonardosanner.arandu_mvp_server.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDTO {

    @Email(message = "O email é invalido, tente novamente no formato: exemplo@email.com")
    public String email;

    @Length(min = 10, message = "A senha deve conter pelo menos 10 caracteres.")
    public String password;
}
