package dev.leonardosanner.arandu_mvp_server.security;

import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
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


@Service
@Deprecated
public class JwtGenerator {

    @Value("${jwt.secret.key.user.login}")
    private String secretKey;

    @Value("${jwt.secret.key.user.login.issuer}")
    private String issuer;

    @Autowired
    private UserRepository userRepository;

    public String generateJWT(Long userId) {
        Instant currentTime = Instant.now();
        SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(userId.toString()) //
                .signWith(secret)
                .issuedAt(Date.from(currentTime))
                .claim("roles", "USER")
                .issuer(issuer)
                .compact();
    }

    public String generateJWT(Long userId, Integer hoursDuration) {
        Instant currentTime = Instant.now();
        SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(userId.toString()) //
                .signWith(secret)
                .issuedAt(Date.from(currentTime))
                .expiration(Date.from(currentTime.plus(Duration.ofHours(hoursDuration))))
                .claims(Map.of("roles", "USER"))
                .issuer(issuer)
                .compact();
    }
}
