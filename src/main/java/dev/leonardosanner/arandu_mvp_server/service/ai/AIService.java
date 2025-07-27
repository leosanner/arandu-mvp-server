package dev.leonardosanner.arandu_mvp_server.service.ai;

import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.UserEventsPlannerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    @Autowired
    private UserEventsPlannerUseCase userEventsPlannerUseCase;

    public String planUserEvents(String userEmail) {

        return this.userEventsPlannerUseCase.execute(userEmail);
    }
}
