package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BasicResponseDTO execute(CreateUserDTO createUserDTO){

        if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent() ||
                userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {

            return BasicResponseDTO.builder()
                    .success(false)
                    .message("Account already exists")
                    .build();
        }

        String fullName = createUserDTO.getFirstName() + " " + createUserDTO.getLastName();

        String encryptPassword = passwordEncoder.encode(createUserDTO.getPassword());

        UserEntity newUser = UserEntity.builder()
                .name(fullName)
                .username(createUserDTO.getUsername())
                .password(encryptPassword)
                .email(createUserDTO.getEmail())
                .build();

        userRepository.save(newUser);

        return BasicResponseDTO.builder()
                .success(true)
                .message("user created")
                .build();
    }
}
