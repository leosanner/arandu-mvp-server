package dev.leonardosanner.arandu_mvp_server.service.user;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateUserDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.LoginUserJWTResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserCredentialsDTO;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.CreateUserUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.LoginUserWithCookiesUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.LoginUserWithJWTUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.VerifyUserCredentialsUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private VerifyUserCredentialsUseCase verifyUserCredentialsUseCase;

    @Autowired
    private LoginUserWithCookiesUseCase loginUserWithCookiesUseCase;

    @Autowired
    private LoginUserWithJWTUseCase loginUserWithJWTUseCase;


    // Create User if email and username do not exist in the DB.
    public ResponseEntity<Object> createUser(CreateUserDTO createUserDTO){
        BasicResponseDTO responseDTO = this.createUserUseCase.execute(createUserDTO);

        return ResponseEntity.accepted().body(responseDTO);
    }


    // FIX: Until now, cookies are not working, see CORS, Server Proxy .... 03/08/2025
    public ResponseEntity<Object> userLoginWithCookies(UserCredentialsDTO userCredentialsDTO,
                                                       HttpServletResponse response){

        BasicResponseDTO responseDTO = this.loginUserWithCookiesUseCase.execute(userCredentialsDTO, response);

        return ResponseEntity.ok().body(responseDTO);
    }

    @Deprecated
    public ResponseEntity<LoginUserJWTResponseDTO> userLoginWithJWT(UserCredentialsDTO userCredentialsDTO) {

        LoginUserJWTResponseDTO responseDTO = this.loginUserWithJWTUseCase.execute(userCredentialsDTO);

        return ResponseEntity.ok().body(responseDTO);
    }
}
