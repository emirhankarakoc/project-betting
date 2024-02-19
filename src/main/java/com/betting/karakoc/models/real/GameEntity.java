package com.betting.karakoc.models.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.enums.GameType;
import com.betting.karakoc.models.requests.CreateGameRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Data
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long betroundId;

    @OneToMany
    @JoinColumn(name = "teamId")
    private List<Team> teams = new ArrayList<>();

    @Enumerated
    private GameType gameType;


    public static GameEntityDTO gameToDto(GameEntity gameParam) {
        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(gameParam.getId());
        dto.setTeams(gameParam.getTeams());
        dto.setBetRoundId(gameParam.getBetroundId());
        return dto;
    }

    public static void isGameEmpty(Optional<GameEntity> game) {
        if (game.isEmpty()) throw new NotfoundException("Invalid Game Id.");
    }

    public static GameEntity createGameBuilder(BetRoundEntity betRound, CreateGameRequest request) {
        //maybe admin is drunk. why not?
        isTeamsSizeEqualsToParam(request.getTeams().size(), request.getTeamsSize());

        GameEntity game = new GameEntity();
        game.setBetroundId(betRound.getId());
        game.setGameType(request.getGameType());
        for (int i = 0; i < request.getTeamsSize(); i++) {
            Team team = new Team();
            team.setName(request.getTeams().get(i));
            team.setScore(0);
            game.teams.add(team);
        }
        return game;
    }


    public static void isTeamsSizeEqualsToParam(int j, int i) {
        if (j != i) throw new BadRequestException("Teams size and given parameter must be equal.");
    }


    public static Integer summaryGameResult(GameEntity game, UserBetRoundEntity userBetRound) {
        GameType type = game.getGameType();
        int correctCounter = 0;
        List<UserBetEntity> usersbets = userBetRound.getUserBetList();
        switch (type) {
            case FOOTBALL: {

                for (UserBetEntity userBet : usersbets) {
                    if (game.getTeams().get(0).getScore() < game.getTeams().get(1).getScore() && Objects.equals(game.getTeams().get(1).getId(), userBet.getBetTeamId()) || game.getTeams().get(0).getScore() > game.getTeams().get(1).getScore() && Objects.equals(game.getTeams().get(0).getId(), userBet.getBetTeamId())) {
                        correctCounter++;
                    }
                }
                return correctCounter;

            }
            case TIME: {
                int smallest = Integer.MAX_VALUE;
                long smallestsId = -1;
                for (int i = 0; i < game.getTeams().size(); i++) {
                    if (smallest > game.getTeams().get(i).getScore()) {
                        smallest = game.getTeams().get(i).getScore();
                        smallestsId = game.getTeams().get(i).getId();
                    }
                }

                for (UserBetEntity userBet : usersbets) {
                    if (smallestsId == userBet.getBetTeamId()) correctCounter++;
                }
                break;
            }
            case SCORE: {
                int highest = -99999;
                long highestId = -1;
                for (int i = 0; i < game.getTeams().size(); i++) {
                    if (highest < game.getTeams().get(i).getScore()) {
                        highest = game.getTeams().get(i).getScore();
                        highestId = game.getTeams().get(i).getId();
                    }
                }
                for (UserBetEntity userBet : usersbets) {
                    if (highestId == userBet.getBetTeamId()) correctCounter++;
                }
                break;
            }
        }
        return correctCounter;
    }

/*    public static List<Team> setGameToTurtle(GameEntity game) {
        Random random = new Random();

        while (true) {
            for (int i = 0; i < game.getTeams().size(); i++) {
                Team currentTeam = game.getTeams().get(i);
                int currentScore = currentTeam.getScore();
                if (currentScore >= 50) return game.getTeams();
                int randomScore = random.nextInt(3) + 1; // 1,2,3
                currentTeam.setScore(currentScore + randomScore);
            }
        }
    }*/
}













