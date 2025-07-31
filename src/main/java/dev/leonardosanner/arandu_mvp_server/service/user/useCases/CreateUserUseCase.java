package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public BasicResponseDTO execute(CreateUserDTO createUserDTO){

    // Verify if username or email already exists.

        if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent() ||
                userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {

            return BasicResponseDTO.builder()
                    .success(false)
                    .message("account already exists")
                    .build();
        }

        String fullName = createUserDTO.getFirstName() + " " + createUserDTO.getLastName();

        UserEntity newUser = UserEntity.builder()
                .name(fullName)
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();

        userRepository.save(newUser);

        return BasicResponseDTO.builder()
                .success(true)
                .message("user created")
                .build();
    }
}
