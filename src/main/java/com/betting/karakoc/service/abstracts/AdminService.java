package com.betting.karakoc.service.abstracts;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    Page<UserEntity> getAllUsers(int pageNumber);

    BetRoundEntityDTO createBetRound(CreateBetRoundRequest request);

    GameEntityDTO createGame(Long betroundId, CreateGameRequest request, int teamsSize);

    List<BetRoundEntityDTO> getCreatedBetRounds();

    GameEntityDTO putGame(Long betroundId, Long gameId, PutGameRequest request);

    BetRoundEntityDTO endBetRound(Long betroundId);

/*
    GameEntity changeGameModuleToTurtleGame(@NotNull Long gameId);
*/


    BetRoundEntityDTO deleteBetRound(Long betroundId);

}
