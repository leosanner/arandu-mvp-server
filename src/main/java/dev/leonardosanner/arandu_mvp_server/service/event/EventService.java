package dev.leonardosanner.arandu_mvp_server.service.event;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.CreateEventUseCase;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUserEventsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private CreateEventUseCase createEventUseCase;

    @Autowired
    private FindUserEventsUseCase findUserEventsUseCase;

    public ResponseEntity<Object> createEvent(CreateEventDTO createEventDTO) {
        this.createEventUseCase.execute(createEventDTO);

        return ResponseEntity.accepted().body("Event registered");
    }

    public List<UserEventInfoDTO> findUserEvents(String userEmail) {
        return this.findUserEventsUseCase.execute(userEmail);
    }
}
