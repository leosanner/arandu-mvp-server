package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindUserEventsUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<UserEventInfoDTO> execute(String userEmail) {
        UserEntity user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not founded")
        );

        List<EventEntity> events = this.eventRepository.findByUser(user);

        return events.stream().map(
                event -> {
                    return UserEventInfoDTO.builder()
                            .userEmail(user.getEmail())
                            .eventName(event.getName())
                            .eventDescription(event.getDescription())
                            .eventStartDate(event.getEventStartDate())
                            .eventEndDate(event.getEventEndDate())
                            .eventLabel(event.getLabel() != null? event.getLabel().getName(): null)
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
