package com.betting.karakoc.repository;


import com.betting.karakoc.models.real.UserBetRoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBetRoundRepository extends JpaRepository<UserBetRoundEntity, String> {

    Optional<UserBetRoundEntity> findByIdAndUserToken(String userBetRoundId, String userToken);

    List<UserBetRoundEntity> findAllByBetRoundEntityId(String betroundId);
}
