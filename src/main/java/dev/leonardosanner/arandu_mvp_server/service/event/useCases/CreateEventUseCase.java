package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public CreateEventUseCase(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public void execute(CreateEventDTO createEventDTO) {
        // TODO: Warn user if a event already exists in the given date
        // TODO: Add option to determine the end of the event "plus".

        // check if user exists
        // TODO: In future steps, user will be authenticated

        UserEntity userEntity = this.userRepository.findByEmail(createEventDTO.getUserEmail()).
                orElseThrow(() -> new RuntimeException("User not founded"));

        LocalDateTime newEventStartDate = LocalDateTime.of(
                createEventDTO.getYear(),
                createEventDTO.getMonth(),
                createEventDTO.getDay(),
                createEventDTO.getHour(),
                createEventDTO.getMinute()
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
