package dev.leonardosanner.arandu_mvp_server.service.ai.threads;

import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.SendNewsByEmailsUseCase;
import java.util.List;

public class NewsApiThread implements Runnable{

    private final SendNewsByEmailsUseCase sendNewsByEmailsUseCase;
    private final UserEntity user;
    private final List<EventEntity> events;

    public NewsApiThread(SendNewsByEmailsUseCase sendNewsByEmailsUseCase,
                         UserEntity user,
                         List<EventEntity> events) {

        this.sendNewsByEmailsUseCase = sendNewsByEmailsUseCase;
        this.user = user;
        this.events = events;
    }

    @Override
    public void run() {
        this.sendNewsByEmailsUseCase.execute(user, events);
    }
}
