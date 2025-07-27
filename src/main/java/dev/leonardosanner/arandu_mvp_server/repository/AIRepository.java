package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.AIMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AIRepository extends JpaRepository<AIMessageEntity, Long> {

    List<AIMessageEntity> findByPromptUserContainingIgnoreCase(String prompt);
}
