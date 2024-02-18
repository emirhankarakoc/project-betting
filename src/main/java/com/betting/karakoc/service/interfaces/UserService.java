package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserEntityDTO;

import java.util.List;

public interface UserService {
    List<BetRoundEntityDTO> getEndedBetRounds();

    List<BetRoundEntityDTO> getPlannedBetRounds();

    UserBetRoundEntityDTO createUserBetRound(Long betRoundEntityId);

    UserBetEntityDTO creteUserBet(Long betRoundId, Long userBetRoundId, Long gameId, Long betTeamId);

    BetRoundEntityDTO getBetroundById(Long id);

    UserEntityDTO changePassword(String username, String password, String newPassword);

}
