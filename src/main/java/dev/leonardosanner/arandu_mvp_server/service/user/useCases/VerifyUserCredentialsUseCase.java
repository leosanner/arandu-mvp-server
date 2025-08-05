package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.InvalidUserCredentialsException;
import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserValidationResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class VerifyUserCredentialsUseCase {

    private final UserRepository userRepository;

    public VerifyUserCredentialsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserValidationResponseDTO execute(UserCredentialsDTO userCredentialsDTO) {
        UserEntity userEntity = this.userRepository.findByEmail(userCredentialsDTO.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("User not founded.")
                );

        //TODO: encrypt passwords
        boolean passwordMatches = userEntity.getPassword().equals(userCredentialsDTO.getPassword());

        if (!passwordMatches) {

            throw  new InvalidUserCredentialsException("Invalid email or password.");
        }

        return UserValidationResponseDTO.builder()
                    .success(true)
                    .message("User founded and has valid credentials.")
                    .userEntity(userEntity)
                    .build();
    }
}
