package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.AiPlanResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.service.ai.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/")
    public ResponseEntity<AiPlanResponseDTO> planEvents(
            @CookieValue(value = "token") String cookieValue) {

        AiPlanResponseDTO responseDTO =  this.aiService.planUserEvents(cookieValue);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/news-api")
    public ResponseEntity<BasicResponseDTO> newsEmail(
            @CookieValue(value = "token") String cookieValue
    ) {
        BasicResponseDTO responseDTO = this.aiService.sendEmail(cookieValue);

        return ResponseEntity.ok().body(responseDTO);
    }
}
