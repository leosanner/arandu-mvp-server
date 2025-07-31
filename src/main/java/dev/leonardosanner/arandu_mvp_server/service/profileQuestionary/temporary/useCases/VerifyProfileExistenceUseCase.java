package dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.TemporaryProfileRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyProfileExistenceUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemporaryProfileRepository temporaryProfileRepository;

    public Boolean execute(String userEmail) {
        UserEntity user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not founded")
        );

        return this.temporaryProfileRepository.findByUser(user).isPresent();
    }
}
