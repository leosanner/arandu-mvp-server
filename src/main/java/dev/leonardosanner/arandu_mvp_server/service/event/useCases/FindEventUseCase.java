package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.EventNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.stereotype.Service;

@Service
public class FindEventUseCase {

    private final EventRepository eventRepository;
    private final UserService userService;

    public FindEventUseCase(EventRepository eventRepository,
                            UserService userService) {

        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public UserEventInfoDTO execute(Long id) {

        UserEntity user = this.userService.getCurrentUser();

        EventEntity eventEntity = this.eventRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new EventNotFoundException("Event not found.")
        );

        return UserEventInfoDTO.builder()
                .createdAt(eventEntity.getCreatedAt())
                .name(eventEntity.getName())
                .userEmail(eventEntity.getUser().getEmail())
                .description(eventEntity.getDescription())
                .startDate(eventEntity.getEventStartDate())
                .build();
    }

}
