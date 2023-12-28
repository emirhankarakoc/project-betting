package com.betting.karakoc.repository;

import com.betting.karakoc.model.real.UserBetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBetRepository extends JpaRepository<UserBetEntity,String> {
    Optional<UserBetEntity> findByGameEntityIdAndUserBetRoundId(Long gameId,Long userBetRoundId);
    List<UserBetEntity> findAllByUserBetRoundId(Long userBetRoundId);
}


