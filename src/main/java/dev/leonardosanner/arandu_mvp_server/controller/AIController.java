package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.service.ai.AIServices;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIServices aiServices;

    @PostMapping("/{userEmail}")
    public String planEvents(@PathVariable String userEmail) {
        return this.aiServices.planUserEvents(userEmail);
    }
}
