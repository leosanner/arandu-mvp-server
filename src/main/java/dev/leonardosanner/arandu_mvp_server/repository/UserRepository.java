package dev.leonardosanner.arandu_mvp_server.repository;

import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmail(String email);
}
