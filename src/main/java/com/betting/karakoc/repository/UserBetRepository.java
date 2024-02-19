package com.betting.karakoc.repository;

import com.betting.karakoc.models.real.UserBetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBetRepository extends JpaRepository<UserBetEntity, String> {
    Optional<UserBetEntity> findByGameEntityIdAndUserBetRoundId(String gameId, String userBetRoundId);

    List<UserBetEntity> findAllByUserBetRoundId(String userBetRoundId);

    List<UserBetEntity> findAllByGameEntityId(String gameId);
}


