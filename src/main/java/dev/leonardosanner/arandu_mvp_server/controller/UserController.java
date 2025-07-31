package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        System.out.println("Chamou a rota POST /user/");
        return userService.createUser(createUserDTO);
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> verifyUserCredentials(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO) {
        return userService.verifyCredentials(userCredentialsDTO);
    }
}
