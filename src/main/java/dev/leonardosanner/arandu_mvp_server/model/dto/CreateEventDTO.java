package dev.leonardosanner.arandu_mvp_server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDTO {

    private String name;
    private String description;

    private Integer day;
    private Integer month;
    private Integer year;
    private Integer hour;
    private Integer minute;

    private String userEmail; // temporary
}
