package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.enums.GameType;
import com.betting.karakoc.model.requests.CreateGameRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

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


    public static GameEntityDTO gameToDtoWithTwoTeams(GameEntity gameParam) {
        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(gameParam.getId());
        dto.setTeams(gameParam.getTeams());
        dto.setBetroundId(gameParam.getBetroundId());
        return dto;
    }

    public static void isGameEmpty(Optional<GameEntity> game) {
        if (game.isEmpty()) throw new NotfoundException("Invalid Game Id.");
    }

    public static GameEntity createGameBuilder(BetRoundEntity betRound, CreateGameRequest request, int teamsSize) {
        isTeamsSizeEqualsToParam(request.getTeams().size(), teamsSize);

        GameEntity game = new GameEntity();
        game.setBetroundId(betRound.getId());
        game.setGameType(request.getGameType());
        for (int i = 0; i < teamsSize; i++) {
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


    public static Integer oyunSonucuHesapla(GameEntity game, UserBetRoundEntity userBetRound) {
        GameType type = game.getGameType();
        int dogruSayisi = 0;
        List<UserBetEntity> adaminBetleri = userBetRound.getUserBetList();
        switch (type) {
            case SCORE: {

                for (UserBetEntity userBet : adaminBetleri) {
                    if (game.getTeams().get(0).getScore() < game.getTeams().get(1).getScore() && Objects.equals(game.getTeams().get(1).getId(), userBet.getBetTeamId()) || game.getTeams().get(0).getScore() > game.getTeams().get(1).getScore() && Objects.equals(game.getTeams().get(0).getId(), userBet.getBetTeamId())) {
                        dogruSayisi++;
                    }
                }
                return dogruSayisi;

            }
            case TIME: {
                int enKucuk = Integer.MAX_VALUE; // Başlangıç değeri büyük bir sayı yapın
                long enKucukId = -1; // Başlangıç değeri geçerli bir ID olmadığından -1 yapın

                for (int i = 0; i < game.getTeams().size(); i++) {
                    if (enKucuk > game.getTeams().get(i).getScore()) {
                        enKucuk = game.getTeams().get(i).getScore();
                        enKucukId = game.getTeams().get(i).getId();
                    }
                }

                for (UserBetEntity userBet : adaminBetleri) {
                    if (enKucukId == userBet.getBetTeamId()) dogruSayisi++;
                }
                break; // switch bloğundan çıkış yapın
            }


        }


        return dogruSayisi;
    }

    public static List<Team> setGameToTurtle(GameEntity game) {
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
    }
}













