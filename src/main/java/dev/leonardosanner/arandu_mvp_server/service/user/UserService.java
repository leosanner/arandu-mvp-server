package dev.leonardosanner.arandu_mvp_server.service.user;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.CreateUserUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.VerifyUserCredentialsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private VerifyUserCredentialsUseCase verifyUserCredentialsUseCase;


    // Create User if email and username do not exist in the DB.
    public ResponseEntity<Object> createUser(CreateUserDTO createUserDTO){
        BasicResponseDTO basicResponseDTO =  createUserUseCase.execute(createUserDTO);
        System.out.println("Chamou service de criar user.");
        return ResponseEntity.accepted().body(basicResponseDTO);
    }

    // Verify user credentials, return a hash containing JWT, user email and matchCredentials
    public ResponseEntity<Object> verifyCredentials(UserCredentialsDTO userCredentialsDTO) {
        HashMap<String, String> hash = verifyUserCredentialsUseCase.execute(userCredentialsDTO);

        return ResponseEntity.ok().body(hash);
    }
}
