package dev.leonardosanner.arandu_mvp_server.service.event;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.CreateEventUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private CreateEventUseCase createEventUseCase;

    public ResponseEntity<Object> createEvent(CreateEventDTO createEventDTO) {
        this.createEventUseCase.execute(createEventDTO);

        return ResponseEntity.accepted().body("Event registered");
    }
}
