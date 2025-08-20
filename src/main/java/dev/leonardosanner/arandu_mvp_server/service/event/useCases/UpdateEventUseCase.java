package dev.leonardosanner.arandu_mvp_server.service.event.useCases;

import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.CronologicalException;
import dev.leonardosanner.arandu_mvp_server.exceptions.exceptionClasses.EventNotFoundException;
import dev.leonardosanner.arandu_mvp_server.mapper.EventUpdateMapper;
import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UpdateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.EventRepository;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UpdateEventUseCase {

    @Autowired
    private EventUpdateMapper eventUpdateMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FindUserByCookieUseCase findUserByCookieUseCase;


    public BasicResponseDTO execute(Long id, String cookieValue, UpdateEventDTO updateEventDTO) {
        UserEntity user = this.findUserByCookieUseCase.execute(cookieValue);

        EventEntity event = this.eventRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new EventNotFoundException("Event not found")
        );

        this.eventUpdateMapper.updateEvent(updateEventDTO, event);
        this.cronologicalSense(event);
        this.eventRepository.save(event);

        return BasicResponseDTO.builder()
                .success(true)
                .message("Evento atualizado com sucesso")
                .build();
    }

    private void cronologicalSense(EventEntity eventEntity) {
        if (eventEntity.getEventEndDate() != null && eventEntity.getEventStartDate() != null) {

            if (eventEntity.getEventEndDate().isBefore(eventEntity.getEventStartDate())) {
                throw new CronologicalException("A data de início deve ser anterior a data de fim do evento.");
            }
        }
    }
}
