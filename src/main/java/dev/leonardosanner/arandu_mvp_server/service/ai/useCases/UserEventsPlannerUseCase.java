package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserEventsPlannerUseCase {

    private final UserEventsPlannerPromptUseCase eventsPlannerPromptUseCase;
    private final ChatClient chatClient;

    public UserEventsPlannerUseCase(ChatClient.Builder builder,
                                    UserEventsPlannerPromptUseCase eventsPlannerPromptUseCase) {
        this.chatClient = builder.build();
        this.eventsPlannerPromptUseCase = eventsPlannerPromptUseCase;
    }

    public String execute(String userEmail) {
        HashMap<String, String> hashMap = this.eventsPlannerPromptUseCase.execute(userEmail);

        return this.chatClient
                .prompt()
                .system(hashMap.get("system"))
                .user(hashMap.get("user"))
                .call()
                .content();
    }
}
