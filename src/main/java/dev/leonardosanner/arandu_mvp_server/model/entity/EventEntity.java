package dev.leonardosanner.arandu_mvp_server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime eventStartDate;

    private LocalDateTime eventEndDate;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private LabelEntity label;

    @ManyToOne
    @NotNull
    @JoinColumn(name= "user_id")
    private UserEntity user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
