package dev.leonardosanner.arandu_mvp_server.service.user.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

// If the credentials match the service must return a hash containing
// ["matchCredentials", "jwt", "userEmail"]
// If did not match, return an empty jwt.

@Service
public class VerifyUserCredentialsUseCase {

    private final UserRepository userRepository;

    public VerifyUserCredentialsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HashMap<String, String> execute(UserCredentialsDTO userCredentialsDTO) {
        UserEntity userEntity = this.userRepository.findByEmail(userCredentialsDTO.getEmail()).
        orElseThrow(
                () -> new UserNotFoundException("User not founded")
        );

        //TODO: encrypt passwords
        var bol = userEntity.getPassword().equals(userCredentialsDTO.getPassword());

        return this.generateHash(bol, userCredentialsDTO.getEmail());
    }

    private HashMap<String, String> generateHash(Boolean bol, String userEmail) {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("userEmail", userEmail);
        hash.put("matchCredentials", bol.toString());

        if (!bol) {
            hash.put("jwt", "-1");

            return hash;
        }
        hash.put("jwt", "test_jwt_for_now");

        return hash;
    }
}
