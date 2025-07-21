package dev.leonardosanner.arandu_mvp_server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventInfoDTO {

    private String userEmail;
    private String eventName;
    private String eventDescription;
    private String eventLabel;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
}
