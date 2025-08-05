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

    private Integer days;
    private Integer month;
    private Integer years;
    private Integer hours;
    private Integer minutes;

}
