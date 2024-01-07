package com.betting.karakoc.model.real;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity@Data
public class UserBetRoundEntity {
    //kupon
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String userEntityId;
    private Long betRoundEntityId;
    @OneToMany
    @JoinColumn(name = "userBetRoundId")
    private List<UserBetEntity> userBetList;
    private int correctGuessedMatchCount;

}
