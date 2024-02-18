package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.UserBetEntityDTO;

import java.util.List;

public interface BetSummaryService {
    List<UserBetEntityDTO> getAllBetsByGame(long betRoundId, long betroundId);

    public String summary(Long userBetRoundId);
}
