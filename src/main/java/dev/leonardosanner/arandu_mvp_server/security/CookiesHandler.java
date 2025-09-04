package dev.leonardosanner.arandu_mvp_server.security;

import dev.leonardosanner.arandu_mvp_server.service.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.time.Duration;
import java.util.Map;


@Service
public class CookiesHandler {

    @Autowired
    private JwtService jwtService;

    @Value("${security.cookies.token.name}")
    private String tokenName;

    public ResponseCookie generateCookie(String userEmail) {

        String jwt = this.jwtService.generateToken(1,
                Map.of("ROLE", "USER"),
                userEmail);

        return ResponseCookie.from(tokenName, jwt) // session cookie
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(Duration.ofHours(1))
                .build();
    }

    public void setCookies(String userEmail, HttpServletResponse response){
        ResponseCookie cookie =  this.generateCookie(userEmail);

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
