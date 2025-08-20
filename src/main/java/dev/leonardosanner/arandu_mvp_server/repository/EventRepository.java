package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity,Long> {

    List<EventEntity> findByUser(UserEntity userEntity);
    Optional<EventEntity> findByIdAndUser(Long id, UserEntity userEntity);
    List<EventEntity> findByEventStartDateBetweenAndUser(LocalDateTime start,
                                                         LocalDateTime end,
                                                         UserEntity user);
}
