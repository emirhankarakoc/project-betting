package com.betting.karakoc.service.abstracts;

import com.betting.karakoc.model.dtos.UserBetEntityDTO;

import java.util.List;

public interface BetSummaryService {
    List<UserBetEntityDTO> getAllBetsByGame(long betRoundId, long betroundId);

    public String summary(Long userBetRoundId);
}
