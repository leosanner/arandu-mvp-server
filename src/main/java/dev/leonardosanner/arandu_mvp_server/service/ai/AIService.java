package dev.leonardosanner.arandu_mvp_server.service.ai;

import dev.leonardosanner.arandu_mvp_server.model.dto.AiPlanResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.SendNewsByEmailsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.UserEventsPlannerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    @Autowired
    private UserEventsPlannerUseCase userEventsPlannerUseCase;

    @Autowired
    private SendNewsByEmailsUseCase sendNewsByEmailsUseCase;

    public AiPlanResponseDTO planUserEvents(String cookieValue
    ) {

        return this.userEventsPlannerUseCase.execute(cookieValue);
    }

    public BasicResponseDTO sendEmail(String cookieValue) {

        return this.sendNewsByEmailsUseCase.execute(cookieValue);
    }
}
