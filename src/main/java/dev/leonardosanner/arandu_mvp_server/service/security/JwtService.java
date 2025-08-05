package dev.leonardosanner.arandu_mvp_server.service.security;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserJWTEntity;
import dev.leonardosanner.arandu_mvp_server.repository.JwtUserRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret.key.user.login}")
    private String secretKey;

    @Value("${jwt.secret.key.user.login.issuer}")
    private String issuer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserRepository jwtUserRepository;

    public String generateToken(Integer hoursDuration, Map<String, ?> claims, String userEmail) {
        SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        UUID identifier = UUID.randomUUID();

        Instant currentTime = Instant.now();

        Instant expiration = Instant.now().plus(Duration.ofHours(hoursDuration));

        UserEntity userEntity = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not founded.")
        );

        this.jwtUserRepository.save(
                UserJWTEntity.builder()
                        .user(userEntity)
                        .expirationDate(expiration)
                        .jwtIdentifier(identifier)
                        .build()
        );

        return Jwts.builder()
                .subject(identifier.toString())
                .signWith(secret)
                .issuedAt(Date.from(currentTime))
                .expiration(Date.from(expiration))
                .claims(claims)
                .issuer(issuer)
                .compact();
    }

    public String getJwiFromToken (String token) {
        SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Jws<Claims> jwsClaims =  Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);

        String subject = jwsClaims.getBody().getSubject();

        System.out.println(jwsClaims.getBody());
        System.out.println(subject);

        return subject;
    }
}
