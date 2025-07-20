package dev.leonardosanner.arandu_mvp_server.service.user;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    public ResponseEntity<Object> createUser(CreateUserDTO createUserDTO){
        createUserUseCase.execute(createUserDTO);

        return ResponseEntity.accepted().body("User created");
    }
}
