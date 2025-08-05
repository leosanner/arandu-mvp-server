package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/")
    public ResponseEntity<Object> createEvent(
            @CookieValue(value = "token") String cookieValue,
            @RequestBody CreateEventDTO createEventDTO) {

        BasicResponseDTO responseDTO = eventService.createEvent(createEventDTO, cookieValue);

        return ResponseEntity.accepted().body(responseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserEvents(
            @CookieValue(value = "token") String cookieValue
    ) {

        List<UserEventInfoDTO> events = this.eventService.findUserEvents(cookieValue);
        return ResponseEntity.ok().body(events);
    }
}
