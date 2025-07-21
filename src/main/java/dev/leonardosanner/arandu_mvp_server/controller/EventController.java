package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.CreateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/")
    public ResponseEntity<Object> createEvent(@RequestBody CreateEventDTO createEventDTO) {

        return this.eventService.createEvent(createEventDTO);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<Object> getUserEvents(@PathVariable String userEmail) {
        System.out.println(userEmail);
        List<UserEventInfoDTO> events = this.eventService.findUserEvents(userEmail);

        return ResponseEntity.ok().body(events);
    }
}
