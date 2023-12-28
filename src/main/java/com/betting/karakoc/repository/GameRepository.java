package com.betting.karakoc.repository;

import com.betting.karakoc.model.real.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity,Long> {
}
