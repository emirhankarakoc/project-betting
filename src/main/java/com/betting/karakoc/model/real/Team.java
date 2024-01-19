package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.NotfoundException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;
    @NotNull@NotBlank@NotEmpty
    private String name;
    @NotNull@NotBlank@NotEmpty
    private int score;


    public static void isTeamEmpty(Optional<Team> team) {
        if (team.isEmpty()) throw new NotfoundException("Invalid team id.");
    }
}
