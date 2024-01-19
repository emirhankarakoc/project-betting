package com.betting.karakoc.service.abstracts;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;

import java.util.List;

public interface UserService {
    List<BetRoundEntityDTO> getEndedBetRounds();

    List<BetRoundEntityDTO> getPlannedBetRounds();

    UserBetRoundEntityDTO createUserBetRound(Long betRoundEntityId);

    UserBetEntityDTO creteUserBet(Long betRoundId, Long userBetRoundId, Long gameId, Long betTeamId);

    BetRoundEntityDTO getBetroundById(Long id);

    UserEntityDTO changePassword(String username, String password, String newPassword);

}
