package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

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

    public void execute(CreateUserDTO createUserDTO){
        this.userRepository.findByEmail(createUserDTO.getEmail()).
                ifPresent(user -> {
                    throw new RuntimeException("user already exists");
                });

        String fullName = createUserDTO.getFirstName() + " " + createUserDTO.getLastName();

        UserEntity newUser = UserEntity.builder()
                .name(fullName)
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();

        userRepository.save(newUser);
    }
}
