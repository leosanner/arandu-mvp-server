package dev.leonardosanner.arandu_mvp_server.model.entity;

// This entity refers to model temporary instructions
// for example: available time for study, goals

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TemporaryProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer availableHours;
    private Integer availableMinutes;

    private String intention;

    private String focus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
