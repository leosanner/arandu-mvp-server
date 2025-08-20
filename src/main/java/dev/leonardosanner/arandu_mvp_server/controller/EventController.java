package dev.leonardosanner.arandu_mvp_server.controller;

import dev.leonardosanner.arandu_mvp_server.model.dto.*;
import dev.leonardosanner.arandu_mvp_server.service.event.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create

    @PostMapping("/")
    public ResponseEntity<Object> createEvent(
            @CookieValue(value = "token") String cookieValue,
            @RequestBody CreateEventDTO createEventDTO) {

        BasicResponseDTO responseDTO = eventService.createEvent(createEventDTO, cookieValue);

        return ResponseEntity.accepted().body(responseDTO);
    }

    // Read

    // Get all user events

    @GetMapping("/")
    public ResponseEntity<Object> getUserEvents(
            @CookieValue(value = "token") String cookieValue
    ) {

        List<UserEventInfoDTO> events = this.eventService.findUserEvents(cookieValue);
        return ResponseEntity.ok().body(events);
    }

    // Get specific event in specific day {month-day-year}

    @GetMapping("/{slugDate}")
    public ResponseEntity<Object> getUserEventsBySlug(
            @CookieValue(value = "token") String cookieValue,
            @PathVariable String slugDate
    ) throws ParseException {

        List<UserEventInfoDTO> events = this.eventService.findUserEventsBySlug(slugDate, cookieValue);

        return ResponseEntity.ok().body(events);
    }

    // Delete

    @DeleteMapping("/delete")
    public ResponseEntity<BasicResponseDTO> deleteEvent(
            @CookieValue(value = "token") String cookieValue
            ,@RequestBody DeleteEventDTO deleteEventDTO) {


        BasicResponseDTO responseDTO =  this.eventService.deleteEvent(deleteEventDTO, cookieValue);

        return ResponseEntity.ok().body(responseDTO);
    }

    // Update

    @PatchMapping("/{id}")
    public ResponseEntity<BasicResponseDTO> updateEvent(
            @RequestParam Long id,
            @Valid @RequestBody UpdateEventDTO updateEventDTO,
            @CookieValue(value = "token") String cookieValue) {

        BasicResponseDTO responseDTO = this.eventService.updateEvent(id, cookieValue, updateEventDTO);

        return ResponseEntity.ok().body(responseDTO);
    }
}
