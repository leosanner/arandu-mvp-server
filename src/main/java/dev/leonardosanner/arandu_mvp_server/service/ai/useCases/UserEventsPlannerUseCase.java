package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class UserEventsPlannerUseCase {

    private final ChatClient chatClient;

    public UserEventsPlannerUseCase(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String execute(String prompt) {
        return chatClient.prompt().system(prompt).call().content();
    }
}
