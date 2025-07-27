package dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.TemporaryProfileRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTemporaryProfileUseCase {

    private final TemporaryProfileRepository temporaryProfileRepository;
    private final UserRepository userRepository;

    public GetTemporaryProfileUseCase(TemporaryProfileRepository temporaryProfileRepository,
                                      UserRepository userRepository) {
        this.temporaryProfileRepository = temporaryProfileRepository;
        this.userRepository = userRepository;
    }

    public TemporaryProfileEntity execute(String userEmail) {
        UserEntity user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not founded")
        );

        return this.temporaryProfileRepository.findByUser(user).orElseThrow(
                () -> new RuntimeException("Profile not made yet")
        );
    }
}
