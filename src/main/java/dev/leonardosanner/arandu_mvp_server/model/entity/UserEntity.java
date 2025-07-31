package dev.leonardosanner.arandu_mvp_server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user_entity")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$\n")
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @Length(min = 10)
    @NotNull
    private String password;
}
