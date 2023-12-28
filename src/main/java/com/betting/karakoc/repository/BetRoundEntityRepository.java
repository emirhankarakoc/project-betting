package com.betting.karakoc.repository;

import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.real.BetRoundEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BetRoundEntityRepository extends JpaRepository<BetRoundEntity,Long> {

     Optional<BetRoundEntity> findById(Long id);

}
