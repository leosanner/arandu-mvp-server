package dev.leonardosanner.arandu_mvp_server.service.ai.threads;

import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import dev.leonardosanner.arandu_mvp_server.service.ai.useCases.SendNewsByEmailsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUpcomingEventsUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class NewsThreadsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindUpcomingEventsUseCase findUpcomingEventsUseCase;

    @Autowired
    private SendNewsByEmailsUseCase sendNewsByEmailsUseCase;

    @Value("${SEARCH_TIME_INTERVAL}")
    private Integer searchTimeInterval;

    @Value("${MIN_EVENTS_REQUIRED}")
    private Integer minEventsRequired;


    public void executeThreads() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            for (UserEntity user: this.userRepository.findAll()) {

                List<EventEntity> userEvents = this.findUpcomingEventsUseCase
                        .execute(searchTimeInterval, user);


                if (userEvents.size() > minEventsRequired) {
                    NewsApiThread apiThread = new NewsApiThread(
                            sendNewsByEmailsUseCase,
                            user,
                            userEvents);

                    executorService.submit(apiThread);
                    }
                else {
                    log.info("User: {} do not have enough events.", user.getName());
                }

                }
            } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } finally {
            executorService.shutdown();
        }
    }
}
