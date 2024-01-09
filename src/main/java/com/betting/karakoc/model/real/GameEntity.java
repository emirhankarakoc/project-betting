package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.requests.CreateGameRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private Long betroundId;

    @OneToMany
    @JoinColumn(name = "teamId")
    private List<Team> teams = new ArrayList<>();



    public static GameEntityDTO gameToDtoWithTwoTeams(GameEntity gameParam){
        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(gameParam.getId());
        dto.setTeams(gameParam.getTeams());
        dto.setBetroundId(gameParam.getBetroundId());
        return dto;
    }
    public static void isGameEmpty(Optional<GameEntity> game){
        if (game.isEmpty()) throw new GeneralException("Invalid Game Id.",400);
    }
    public static GameEntity createGameBuilder(BetRoundEntity betRound, CreateGameRequest request, int teamsSize){
        isTeamsSizeEqualsToParam(request.getTeams().size(),teamsSize);

        GameEntity game = new GameEntity();
        game.setBetroundId(betRound.getId());
        for (int i = 0;i<teamsSize;i++){
            game.teams.add(new Team(UUID.randomUUID().toString(), request.getTeams().get(i), 0));
        }
        return game;}

    
    public static void isTeamsSizeEqualsToParam(int j, int i){
        if (j!=i) throw new GeneralException("Teams size and given parameter must be equal.",400);
    }
}
