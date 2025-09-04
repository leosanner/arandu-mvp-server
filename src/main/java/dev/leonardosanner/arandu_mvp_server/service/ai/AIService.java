package dev.leonardosanner.arandu_mvp_server.service.ai;

import dev.leonardosanner.arandu_mvp_server.model.dto.AiPlanResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.SendNewsByEmailsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.UserEventsPlannerUseCase;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUpcomingEventsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    @Deprecated
    @Autowired
    private UserEventsPlannerUseCase userEventsPlannerUseCase;

    @Autowired
    private UserService userService;

    @Autowired
    private SendNewsByEmailsUseCase sendNewsByEmailsUseCase;

    @Autowired
    private FindUpcomingEventsUseCase findUpcomingEventsUseCase;

    @Deprecated
    public AiPlanResponseDTO planUserEvents(String cookieValue
    ) {

        return this.userEventsPlannerUseCase.execute(cookieValue);
    }

    public BasicResponseDTO sendEmail() {

        UserEntity user = this.userService.getCurrentUser();
        List<EventEntity> userEvents = this.findUpcomingEventsUseCase
                .execute(7, user);

        return this.sendNewsByEmailsUseCase.execute(user, userEvents);
    }
}
