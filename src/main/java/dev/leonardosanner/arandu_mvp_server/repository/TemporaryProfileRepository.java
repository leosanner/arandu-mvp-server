package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporaryProfileRepository extends JpaRepository<TemporaryProfileEntity, Long> {
    Optional<TemporaryProfileEntity> findByUser(UserEntity user);
}
