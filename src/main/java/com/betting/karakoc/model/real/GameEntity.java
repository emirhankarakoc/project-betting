package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.requests.CreateGameRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private Long betroundId;
    private String firstTeam;
    private String secondTeam;
    int scoreFirstTeam;
    int scoreSecondTeam;


    public static GameEntity createGameBuilder(Optional<BetRoundEntity> betRound, CreateGameRequest request){
        GameEntity game = new GameEntity();
        game.setBetroundId(betRound.get().getId());
        game.setFirstTeam(request.getFirstTeam());
        game.setScoreFirstTeam(0);
        game.setSecondTeam(request.getSecondTeam());
        game.setScoreSecondTeam(0);
            return game;
    }

    public static GameEntityDTO gameToDto(GameEntity game){
        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(game.getId());
        dto.setBetroundId(game.getBetroundId());
        dto.setFirstTeam(game.getFirstTeam());
        dto.setSecondTeam(game.getSecondTeam());
        dto.setScoreFirstTeam(game.getScoreFirstTeam());
        dto.setScoreSecondTeam(game.getScoreSecondTeam());
        return dto;
    }
    public static void isGameEmpty(Optional<GameEntity> game){
        if (game.isEmpty()) throw new GeneralException("Invalid Game Id.",400);
    }
}
