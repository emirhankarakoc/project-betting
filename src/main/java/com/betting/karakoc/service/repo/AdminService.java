package com.betting.karakoc.service.repo;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.real.BetRoundEntity;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    Page<UserEntity> getAllUsers(String token, int pageNumber);
    BetRoundEntityDTO createBetRound(CreateBetRoundRequest request, String token);
    GameEntityDTO createGame(Long betroundId,CreateGameRequest request, String token);
    List<BetRoundEntityDTO> getCreatedBetRounds(String token);
    GameEntityDTO putGame(PutGameRequest request, String token);
    BetRoundEntityDTO endBetRound(Long betroundId, String token);
}
