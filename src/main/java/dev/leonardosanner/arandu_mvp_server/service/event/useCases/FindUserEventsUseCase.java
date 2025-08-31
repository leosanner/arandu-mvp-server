package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.UserNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindUserEventsUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;


    public List<UserEventInfoDTO> execute(UserEntity userEntity) {
        return this.execute(userEntity.getEmail());
    }

    public List<UserEventInfoDTO> execute(String userEmail) {
        UserEntity user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not found.")
        );

        List<EventEntity> events = this.findValidUserEvents(user);

        return events.stream().map(
                event -> {
                    return UserEventInfoDTO.builder()
                            .id(event.getId())
                            .userEmail(user.getEmail())
                            .name(event.getName())
                            .description(event.getDescription())
                            .startDate(event.getEventStartDate())
                            .endDate(event.getEventEndDate())
                            .label(event.getLabel() != null? event.getLabel().getName(): null)
                            .createdAt(event.getCreatedAt())
                            .build();
                }
        ).collect(Collectors.toList());
    }

    private List<EventEntity> findValidUserEvents(UserEntity user) {
        List<EventEntity> events = this.eventRepository.findByUser(user);
        LocalDateTime localDateTime = LocalDateTime.now();

        for (int i = events.size()-1; i>=0; i--) {

            var currentEvent = events.get(i);

            if (currentEvent.getEventStartDate().isBefore(localDateTime)) {
                this.eventRepository.delete(currentEvent);
                events.remove(i);
            }
        }

        return events;
    }
}
