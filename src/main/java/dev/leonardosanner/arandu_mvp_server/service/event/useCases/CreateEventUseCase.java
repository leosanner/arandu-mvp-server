package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.CronologicalException;
import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.InvalidUserCredentialsException;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {

        this.eventRepository = eventRepository;
    }

    public void execute(CreateEventDTO createEventDTO, UserEntity userEntity) {
        // TODO: Warn user if a event already exists in the given date
        // TODO: Add option to determine the end of the event "plus".

        // TODO: In future steps, user will be authenticated -> use auth userUseCase


        LocalDateTime newEventStartDate = LocalDateTime.of(
                createEventDTO.getYears(),
                createEventDTO.getMonth(),
                createEventDTO.getDays(),
                createEventDTO.getHours(),
                createEventDTO.getMinutes()
        );

        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isAfter(newEventStartDate)) {
            throw new CronologicalException("O evento é anterior ao momento presente");
        }

        EventEntity newEvent = EventEntity.builder()
                .name(createEventDTO.getName())
                .description(createEventDTO.getDescription())
                .eventStartDate(newEventStartDate)
                .user(userEntity)
                .build();

        this.eventRepository.save(newEvent);
    }
}
