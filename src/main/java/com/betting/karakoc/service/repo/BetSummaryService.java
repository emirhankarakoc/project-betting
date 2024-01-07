package com.betting.karakoc.service.repo;

import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.real.AllInOneEntity;
import com.betting.karakoc.model.real.UserBetEntity;
import com.betting.karakoc.model.requests.ResponseAllBets;

import java.util.List;

public interface BetSummaryService {
    List<UserBetEntityDTO> getAllBetsByGame(long betRoundId);
    String summaryBets(Long betRoundId, String token);
}
