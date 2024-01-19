package com.betting.karakoc.repository;

import com.betting.karakoc.model.real.BetRoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BetRoundEntityRepository extends JpaRepository<BetRoundEntity, Long> {

    Optional<BetRoundEntity> findById(Long id);

}
