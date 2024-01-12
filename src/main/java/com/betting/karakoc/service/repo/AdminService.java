package com.betting.karakoc.service.repo;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.real.GameEntity;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequestWithTwoTeams;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    Page<UserEntity> getAllUsers(int pageNumber);
    BetRoundEntityDTO createBetRound(CreateBetRoundRequest request);
    GameEntityDTO createGame(Long betroundId, CreateGameRequest request, int teamsSize);
    List<BetRoundEntityDTO> getCreatedBetRounds();
    GameEntityDTO putGame(PutGameRequestWithTwoTeams request);
    BetRoundEntityDTO endBetRound(Long betroundId);
    GameEntity changeGameToModule(Long gameId);
}
