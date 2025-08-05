package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.LoginUserJWTResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserValidationResponseDTO;
import dev.leonardosanner.arandu_mvp_server.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginUserWithJWTUseCase {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VerifyUserCredentialsUseCase verifyUserCredentialsUseCase;

    public LoginUserJWTResponseDTO execute(UserCredentialsDTO userCredentialsDTO){

        UserValidationResponseDTO response = this.verifyUserCredentialsUseCase.execute(userCredentialsDTO);

        String jwtToken =  this.jwtService.generateToken(1,
                Map.of("ROLE", "USER"),
                response.getUserEntity().getEmail());

        return LoginUserJWTResponseDTO.builder()
                .success(true)
                .token(jwtToken)
                .message("Json Web Token generated.")
                .build();
    }
}
