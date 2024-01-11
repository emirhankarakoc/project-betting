package com.betting.karakoc.service.repo;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.real.BetRoundEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface UserService {
     List<BetRoundEntityDTO> getEndedBetRounds();
     List<BetRoundEntityDTO> getPlannedBetRounds();
     UserBetRoundEntityDTO createUserBetRound(Long betRoundEntityId);
     UserBetEntityDTO creteUserBet(Long userBetRoundId, Long gameId, Long betTeamId);
     BetRoundEntityDTO getBetroundById(Long id);
     UserEntityDTO changePassword(String username, String password, String newPassword);

}
