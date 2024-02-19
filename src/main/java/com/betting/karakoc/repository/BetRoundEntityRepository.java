package com.betting.karakoc.repository;

import com.betting.karakoc.models.enums.BetStatus;
import com.betting.karakoc.models.real.BetRoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BetRoundEntityRepository extends JpaRepository<BetRoundEntity, String> {

    Optional<BetRoundEntity> findById(String id);
    List<BetRoundEntity> findAllByBetStatus(BetStatus betStatus);

}
