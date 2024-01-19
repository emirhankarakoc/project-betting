package com.betting.karakoc.model.dtos;


import com.betting.karakoc.model.real.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameEntityDTO {
    private Long id;
    private Long betRoundId;
    private List<Team> teams = new ArrayList<>();
}
