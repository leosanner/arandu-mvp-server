package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.AiPlanResponseDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Deprecated
@Service
public class UserEventsPlannerUseCase {

    private final UserEventsPlannerPromptUseCase eventsPlannerPromptUseCase;
    private final ChatClient chatClient;

    public UserEventsPlannerUseCase(ChatClient.Builder builder,
                                    UserEventsPlannerPromptUseCase eventsPlannerPromptUseCase) {
        this.chatClient = builder.build();
        this.eventsPlannerPromptUseCase = eventsPlannerPromptUseCase;
    }

    public AiPlanResponseDTO execute(String cookieValue) {
        HashMap<String, String> hashMap = this.eventsPlannerPromptUseCase.execute(cookieValue);

        String content = this.chatClient
                .prompt()
                .system(hashMap.get("system"))
                .user(hashMap.get("user"))
                .call()
                .content();

        return AiPlanResponseDTO.builder().message(content).build();
    }
}
