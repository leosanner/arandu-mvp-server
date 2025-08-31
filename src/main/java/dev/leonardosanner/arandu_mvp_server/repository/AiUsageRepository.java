package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.AiUsageEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiUsageRepository extends JpaRepository<AiUsageEntity, Integer> {

    Optional<AiUsageEntity> findByUser(UserEntity user);
}
