package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDTO createUserDTO){
        return userService.createUser(createUserDTO);
    }
}
