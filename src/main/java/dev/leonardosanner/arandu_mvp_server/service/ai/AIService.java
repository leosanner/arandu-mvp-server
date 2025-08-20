package dev.leonardosanner.arandu_mvp_server.service.ai;

import dev.leonardosanner.arandu_mvp_server.model.dto.AiPlanResponseDTO;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.UserEventsPlannerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

@Service
public class AIService {

    @Autowired
    private UserEventsPlannerUseCase userEventsPlannerUseCase;

    public AiPlanResponseDTO planUserEvents(String cookieValue
    ) {

        return this.userEventsPlannerUseCase.execute(cookieValue);
    }
}
