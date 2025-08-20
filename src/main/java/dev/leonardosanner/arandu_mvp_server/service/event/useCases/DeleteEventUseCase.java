package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.EventNotFoundException;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.DeleteEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteEventUseCase {

    private final EventRepository eventRepository;
    private final FindUserByCookieUseCase findUserByCookieUseCase;

    public DeleteEventUseCase(EventRepository eventRepository,
                              FindUserByCookieUseCase findUserByCookieUseCase) {
        this.eventRepository = eventRepository;
        this.findUserByCookieUseCase = findUserByCookieUseCase;
    }

    public BasicResponseDTO execute(DeleteEventDTO deleteEventDTO, String cookieValue) {

        UserEntity userEntity = this.findUserByCookieUseCase.execute(cookieValue);

        EventEntity eventEntity = this.eventRepository.findByIdAndUser(deleteEventDTO.getId(),
                userEntity
                ).orElseThrow(
                () -> new EventNotFoundException("Event not found.")
        );

        this.eventRepository.delete(eventEntity);

        return BasicResponseDTO.builder()
                .success(true)
                .message("Event deleted.")
                .build();
    }
}
