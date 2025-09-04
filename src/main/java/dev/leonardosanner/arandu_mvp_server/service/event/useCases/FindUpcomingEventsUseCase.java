package dev.leonardosanner.arandu_mvp_server.service.event.useCases;


import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindUpcomingEventsUseCase {

    private final EventRepository eventRepository;
    private final UserService userService;

    public FindUpcomingEventsUseCase(EventRepository eventRepository,
                                     UserService userService) {

        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<EventEntity> execute(Integer timeInterval, UserEntity user) {

        LocalDateTime period = LocalDateTime.now().plusDays(timeInterval);
        List<EventEntity> events = this.eventRepository.findByUser(user);

        for (int i = events.size()-1; i >= 0; i--) {
            EventEntity event = events.get(i);

            if (event.getEventStartDate().isAfter(period)) {
                events.remove(i);
            }
        }

        return events;
    }
}
