package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserJWTEntity;
import dev.leonardosanner.arandu_mvp_server.repository.JwtUserRepository;
import dev.leonardosanner.arandu_mvp_server.service.security.JwtService;
import dev.leonardosanner.arandu_mvp_server.utils.CookiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class FindUserByCookieUseCase {

    @Autowired
    private JwtUserRepository jwtUserRepository;

    @Autowired
    private JwtService jwtService;

    public UserEntity execute(String cookieToken) {

        String subject = this.jwtService.getJwiFromToken(cookieToken);

        UserJWTEntity entity = this.jwtUserRepository.findByJwtIdentifier(
                UUID.fromString(subject)
        ).orElseThrow(
                () -> new UserNotFoundException("User not found.")
        );

        return entity.getUser();
    }
}
