package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateBetRoundRequest;
import com.betting.karakoc.models.requests.CreateGameRequest;
import com.betting.karakoc.models.requests.PutGameRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    Page<UserEntity> getAllUsers(int pageNumber);

    BetRoundEntityDTO createBetRound(CreateBetRoundRequest request);

    GameEntityDTO createGame(CreateGameRequest request);

    List<BetRoundEntityDTO> getCreatedBetRounds();

    GameEntityDTO putGame(Long betroundId, Long gameId, PutGameRequest request);

    BetRoundEntityDTO endBetRound(Long betroundId);

/*
    GameEntity changeGameModuleToTurtleGame(@NotNull Long gameId);
*/


    void deleteBetRound(Long betroundId);

    void deleteGame(Long betroundId, Long gameId);

    void deleteUserBetRound(Long betroundId, Long userbetroundId);
     void deleteBet(Long betroundId, Long userbetroundId, String betId);


    }
