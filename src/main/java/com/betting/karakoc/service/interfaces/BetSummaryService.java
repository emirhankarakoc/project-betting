package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.requests.GetAllBetsByGameRequest;
import com.betting.karakoc.models.requests.SummaryRequest;

import java.util.List;

public interface BetSummaryService {
    List<UserBetEntityDTO> getAllBetsByGame(GetAllBetsByGameRequest request);

    public String summary(SummaryRequest request);
}
