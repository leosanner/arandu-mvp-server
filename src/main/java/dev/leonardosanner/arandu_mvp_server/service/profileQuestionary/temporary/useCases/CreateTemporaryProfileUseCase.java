package dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.TemporaryProfileRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTemporaryProfileUseCase {

    private final TemporaryProfileRepository temporaryProfileRepository;
    private final UserRepository userRepository;

    public CreateTemporaryProfileUseCase(TemporaryProfileRepository temporaryProfileRepository,
                                      UserRepository userRepository) {

        this.temporaryProfileRepository = temporaryProfileRepository;
        this.userRepository = userRepository;
    }

    public Boolean execute(String userEmail, TemporaryProfileEntity profileEntity) {
        UserEntity user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not founded")
        );

        profileEntity.setUser(user);

        this.temporaryProfileRepository.findByUser(user).ifPresent(
                entity -> {
                    throw new RuntimeException("Profile already exists");
                }
        );

        this.temporaryProfileRepository.save(profileEntity);

        return true;
    }
}
