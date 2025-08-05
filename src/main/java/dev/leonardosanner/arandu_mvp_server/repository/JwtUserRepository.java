package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.UserJWTEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JwtUserRepository extends JpaRepository<UserJWTEntity, Long> {

    Optional<UserJWTEntity> findByJwtIdentifier(UUID identifier);
}
