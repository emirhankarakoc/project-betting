package com.betting.karakoc.model.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PutGameRequestWithTwoTeams {
    public Long gameId;

    public List<Integer> scores;


}
