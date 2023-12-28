package com.betting.karakoc.model.dtos;

import com.betting.karakoc.model.enums.Selection;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserBetEntityDTO {
    private String id;
    private Long userBetRoundId;
    private Long gameEntityId;
    private Selection selection;
    private Boolean isGuessCorrect;

    private String oynayanTakimlar;
}
