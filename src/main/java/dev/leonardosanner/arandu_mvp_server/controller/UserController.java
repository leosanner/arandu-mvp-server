package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.LoginUserJWTResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        return userService.createUser(createUserDTO);
    }

    @PostMapping("/login/jwt")
    public ResponseEntity<LoginUserJWTResponseDTO> userLoginJWT(
            @Valid @RequestBody UserCredentialsDTO userCredentialsDTO) {

        return userService.userLoginWithJWT(userCredentialsDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLoginCookies(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO,
                                                   HttpServletResponse response) {

        return this.userService.userLoginWithCookies(userCredentialsDTO, response);
    }
}
