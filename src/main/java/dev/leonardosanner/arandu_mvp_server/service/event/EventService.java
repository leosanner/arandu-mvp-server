package dev.leonardosanner.arandu_mvp_server.service.event;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.InvalidUserCredentialsException;
import dev.leonardosanner.arandu_mvp_server.model.dto.*;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.*;
import dev.leonardosanner.arandu_mvp_server.service.user.UserService;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private UserService userService;

    @Autowired
    private CreateEventUseCase createEventUseCase;

    @Autowired
    private FindUserEventsUseCase findUserEventsUseCase;

    @Autowired
    private FindUserEventBySlugUseCase findUserEventBySlugUseCase;

    @Autowired
    private DeleteEventUseCase deleteEventUseCase;

    @Autowired
    private FindEventUseCase findEventUseCase;

    @Autowired
    private UpdateEventUseCase updateEventUseCase;


    public BasicResponseDTO createEvent(CreateEventDTO createEventDTO) {
        UserEntity user = this.userService.getCurrentUser();
        this.createEventUseCase.execute(createEventDTO, user);


        return BasicResponseDTO.builder()
                .success(true)
                .message("Event Created.")
                .build();
    }

    public List<UserEventInfoDTO> findUserEvents() {
        UserEntity user = this.userService.getCurrentUser();

        return this.findUserEventsUseCase.execute(user);
    }

    public BasicResponseDTO deleteEvent(DeleteEventDTO deleteEventDTO) {
        UserEntity user = this.userService.getCurrentUser();

        return this.deleteEventUseCase.execute(deleteEventDTO, user);
    }

    public UserEventInfoDTO findEvent(Long id) {
        return this.findEventUseCase.execute(id);
    }

    public BasicResponseDTO updateEvent(Long id,
                                        String cookieValue,
                                        UpdateEventDTO updateEventDTO) {

        return this.updateEventUseCase.execute(id, cookieValue, updateEventDTO);
    }

    public List<UserEventInfoDTO> findUserEventsBySlug(String slug, String cookieValue)
            throws ParseException {

        return this.findUserEventBySlugUseCase.execute(slug, cookieValue);
    }
}
