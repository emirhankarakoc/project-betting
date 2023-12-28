package com.betting.karakoc.model.requests;


import com.betting.karakoc.model.enums.Selection;
import lombok.Data;

@Data
public class CreateUserBetRequest {
    private Long userBetRoundId;
    private Long gameId;
    private Selection selection;
}
