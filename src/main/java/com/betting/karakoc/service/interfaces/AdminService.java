package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    Page<UserEntity> getAllUsers(GetAllUsersRequest request);

    BetRoundEntityDTO createBetRound(CreateBetRoundRequest request);

    GameEntityDTO createGame(CreateGameRequest request) throws InterruptedException;

    List<BetRoundEntityDTO> getCreatedBetRounds(GetCreatedBetRoundsRequest request);

    GameEntityDTO putGame(PutGameRequest request);

    BetRoundEntityDTO endBetRound(EndBetRoundRequest request);

/*
    GameEntity changeGameModuleToTurtleGame(@NotNull Long gameId);
*/


    void deleteBetRound(DeleteBetRoundRequest request);

    void deleteGame(DeleteGameRequest request);

    void deleteUserBetRound(DeleteUserBetRoundRequest request);
     void deleteBet(DeleteBetRequest request);


    }
