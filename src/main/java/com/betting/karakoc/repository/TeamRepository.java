package com.betting.karakoc.repository;

import com.betting.karakoc.models.real.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
