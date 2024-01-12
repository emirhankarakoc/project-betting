package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int score;


    public static void isTeamEmpty(Optional<Team> team){
        if (team.isEmpty()) throw new GeneralException("Invalid team id.",400);
    }
}
