package com.betting.karakoc.model.real;


import com.betting.karakoc.model.enums.Selection;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserBetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long userBetRoundId;
    private Long gameEntityId;
    @Enumerated
    private Selection selection; // 1  0  2
    private Boolean isGuessCorrect;


}
