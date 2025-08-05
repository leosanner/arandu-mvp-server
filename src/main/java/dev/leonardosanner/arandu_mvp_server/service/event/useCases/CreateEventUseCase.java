package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FindUserByCookieUseCase findUserByCookieUseCase;

    public CreateEventUseCase(EventRepository eventRepository,
                              UserRepository userRepository,
                              FindUserByCookieUseCase findUserByCookieUseCase) {

        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.findUserByCookieUseCase = findUserByCookieUseCase;
    }

    public void execute(CreateEventDTO createEventDTO, String cookieValue) {
        // TODO: Warn user if a event already exists in the given date
        // TODO: Add option to determine the end of the event "plus".

        // check if user exists
        // TODO: In future steps, user will be authenticated -> use auth userUseCase

        UserEntity userEntity = this.findUserByCookieUseCase.execute(cookieValue);

        LocalDateTime newEventStartDate = LocalDateTime.of(
                createEventDTO.getYears(),
                createEventDTO.getMonth(),
                createEventDTO.getDays(),
                createEventDTO.getHours(),
                createEventDTO.getMinutes()
        );

        EventEntity newEvent = EventEntity.builder()
                .name(createEventDTO.getName())
                .description(createEventDTO.getDescription())
                .eventStartDate(newEventStartDate)
                .user(userEntity)
                .build();

        this.eventRepository.save(newEvent);
    }
}
