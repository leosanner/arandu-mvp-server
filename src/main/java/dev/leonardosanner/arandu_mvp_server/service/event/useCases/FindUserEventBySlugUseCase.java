package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindUserEventBySlugUseCase {

    private final EventRepository eventRepository;
    private final FindUserByCookieUseCase findUserByCookieUseCase;

    public FindUserEventBySlugUseCase(
            EventRepository eventRepository,
            FindUserByCookieUseCase findUserByCookieUseCase
    ) {
        this.eventRepository = eventRepository;
        this.findUserByCookieUseCase = findUserByCookieUseCase;
    }

    public List<UserEventInfoDTO> execute(String slug, String cookieValue) throws ParseException {

        // Parse Date and get local date time

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = sdf.parse(slug);

        LocalDateTime start = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime end = start.plusDays(1);

        // Search interval current + 1

        UserEntity user = this.findUserByCookieUseCase.execute(cookieValue);

        List<EventEntity> events = this.eventRepository.
                findByEventStartDateBetweenAndUser(start, end, user);

        return events.stream().map(
                event-> {
                    return UserEventInfoDTO.builder()
                            .id(event.getId())
                            .userEmail(user.getEmail())
                            .name(event.getName())
                            .description(event.getDescription())
                            .startDate(event.getEventStartDate())
                            .endDate(event.getEventEndDate())
                            .label(event.getLabel() != null? event.getLabel().getName(): null)
                            .createdAt(event.getCreatedAt())
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
