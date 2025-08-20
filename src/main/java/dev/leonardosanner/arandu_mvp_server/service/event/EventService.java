package dev.leonardosanner.arandu_mvp_server.service.event;

import dev.leonardosanner.arandu_mvp_server.model.dto.*;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.*;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private FindUserByCookieUseCase findUserByCookieUseCase;

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

    public BasicResponseDTO deleteEvent(DeleteEventDTO deleteEventDTO, String cookieValue) {
        return this.deleteEventUseCase.execute(deleteEventDTO, cookieValue);
    }

    public UserEventInfoDTO findEvent(Long id, String cookieValue) {
        return this.findEventUseCase.execute(id, cookieValue);
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
