package dev.leonardosanner.arandu_mvp_server.service.ai;

import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.UserEventsPlannerPromptUseCase;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIServices {

    @Autowired
    private UserEventsPlannerPromptUseCase userEventsPlannerPromptUseCase;

    private final ChatClient chatClient;

    public AIServices(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String planUserEvents(String userEmail) {
        String prompt = userEventsPlannerPromptUseCase.execute(userEmail);

        return this.chatClient.prompt().system(prompt).call().content();
    }
}
