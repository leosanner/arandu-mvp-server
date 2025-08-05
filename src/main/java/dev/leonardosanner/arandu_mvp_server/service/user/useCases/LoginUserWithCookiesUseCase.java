package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserValidationResponseDTO;
import dev.leonardosanner.arandu_mvp_server.security.CookiesHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserWithCookiesUseCase {

    @Autowired
    private CookiesHandler cookiesHandler;

    @Autowired
    private VerifyUserCredentialsUseCase verifyUserCredentialsUseCase;

    public BasicResponseDTO execute(UserCredentialsDTO userCredentialsDTO, HttpServletResponse response) {
        UserValidationResponseDTO validCredentials =
                this.verifyUserCredentialsUseCase.execute(userCredentialsDTO);

        cookiesHandler.setCookies(validCredentials.getUserEntity().getEmail(), response);

        return BasicResponseDTO.builder()
                    .success(true)
                    .message("User successfully authenticated and session initialized.")
                    .build();
    }
}
