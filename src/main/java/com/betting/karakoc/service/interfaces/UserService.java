package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.requests.*;

import java.util.List;

public interface UserService {
    List<BetRoundEntityDTO> getEndedBetRounds(GetEndedBetRoundsRequest request);

    List<BetRoundEntityDTO> getPlannedBetRounds(GetPlannedBetRoundsRequest request);

    UserBetRoundEntityDTO createUserBetRound(CreateUserBetRoundRequest request);

    UserBetEntityDTO creteUserBet(CreateBetRequest request );

    BetRoundEntityDTO getBetroundById(String id);

    UserEntityDTO changePassword(ChangePasswordRequest request);

}
