package com.betting.karakoc.repository;


import com.betting.karakoc.model.real.BetRoundEntity;
import com.betting.karakoc.model.real.UserBetRoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBetRoundRepository extends JpaRepository<UserBetRoundEntity,Long> {

     UserBetRoundEntity findByUserEntityId(String userId);

     UserBetRoundEntity findByBetRoundEntityIdAndUserEntityId(Long betroundId, String userId);
     Optional<UserBetRoundEntity> findByIdAndUserEntityId(Long userBetRoundId,String userId);
     BetRoundEntity findByBetRoundEntityId(Long betroundId);
     BetRoundEntity findBetRoundEntityByBetRoundEntityId(Long betRoundId);

     List<UserBetRoundEntity> findAllByBetRoundEntityId(Long betroundId);
}
