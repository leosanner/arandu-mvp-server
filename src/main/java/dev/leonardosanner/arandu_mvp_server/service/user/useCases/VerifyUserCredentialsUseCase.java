package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.UserLoginDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;

public class VerifyUserCredentialsUseCase {

    private final UserRepository userRepository;

    public VerifyUserCredentialsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserLoginDTO userLoginDTO) {
        UserEntity userEntity = this.userRepository.findByEmail(userLoginDTO.getEmail()).
        orElseThrow(
                () -> new RuntimeException("User not founded")
        );

        // encrypt passwords and verify if matches
    }
}
