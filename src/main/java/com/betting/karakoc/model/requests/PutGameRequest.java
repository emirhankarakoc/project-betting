package com.betting.karakoc.model.requests;


import com.betting.karakoc.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutGameRequest {
    public Long gameId;
    int scoreFirstTeam;
    int scoreSecondTeam;

}
