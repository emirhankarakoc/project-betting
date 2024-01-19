package com.betting.karakoc.repository;

import com.betting.karakoc.model.real.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
