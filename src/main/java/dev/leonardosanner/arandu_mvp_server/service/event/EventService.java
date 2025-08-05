package dev.leonardosanner.arandu_mvp_server.service.event;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.CreateEventUseCase;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUserEventsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private FindUserByCookieUseCase findUserByCookieUseCase;

    @Autowired
    private CreateEventUseCase createEventUseCase;

    @Autowired
    private FindUserEventsUseCase findUserEventsUseCase;

    public BasicResponseDTO createEvent(CreateEventDTO createEventDTO, String cookieValue) {
        this.createEventUseCase.execute(createEventDTO, cookieValue);

        return BasicResponseDTO.builder()
                .success(true)
                .message("Event Created.")
                .build();
    }

    public List<UserEventInfoDTO> findUserEvents(String cookies) {
        UserEntity userEntity = this.findUserByCookieUseCase.execute(cookies);

        return this.findUserEventsUseCase.execute(userEntity);
    }
}
