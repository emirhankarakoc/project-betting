package com.betting.karakoc.service.implementations;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequest;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.service.abstracts.AdminService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.betting.karakoc.model.real.BetRoundEntity.*;
import static com.betting.karakoc.model.real.GameEntity.*;
import static com.betting.karakoc.model.real.UserBetEntity.isUserBetEmpty;
import static com.betting.karakoc.model.real.UserBetRoundEntity.isUserBetRoundEmpty;

@Data
@Service
@AllArgsConstructor


public class AdminManager implements AdminService {

    private final UserEntityRepository userRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final BetRoundEntityRepository betRoundRepository;
    private final SecurityContextUtil securityContextUtil;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final UserBetRepository userBetRepository;

    @Transactional
    public GameEntityDTO createGame( Long betroundId,  CreateGameRequest request,  int teamsSize) {

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        isBetroundsGameSizeLowerThanXX(betRound.get());
        GameEntity game = createGameBuilder(betRound.get(), request, teamsSize);
        gameRepository.save(game);
        teamRepository.saveAll(game.getTeams());
        betRound.get().getGames().add(game);
        betRoundRepository.save(betRound.get());
        setPlannedIfGamesSizeIsXX(betRound.get());
        return gameToDto(game);
    }

    public BetRoundEntityDTO createBetRound( CreateBetRoundRequest request) {

        isPlayDatePast(request.getPlayDateTime());
        BetRoundEntity betRound = createBetRoundBuilder(request);
        betRoundRepository.save(betRound);
        return betroundToDto(betRound);

    }

    public Page<UserEntity> getAllUsers( int pageNumber) {
        return userRepository.findAll(PageRequest.of(pageNumber, 20, Sort.Direction.DESC, "createddatetime"));
    }

    public List<BetRoundEntityDTO> getCreatedBetRounds() {

        List<BetRoundEntity> betrounds = betRoundRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();
        for (BetRoundEntity betRound : betrounds) {
            if (betRound.getBetStatus() == BetStatus.CREATED) {
                responseList.add(betroundToDto(betRound));
            }
        }
        return responseList;
    }


    public BetRoundEntityDTO endBetRound( Long betroundId) {

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        betRound.get().setBetStatus(BetStatus.ENDED);
        betRoundRepository.save(betRound.get());
        return betroundToDto(betRound.get());
    }

    @Transactional
    public GameEntityDTO putGame( Long betRoundId,  Long gameId,  PutGameRequest request) {

        Optional<GameEntity> game = gameRepository.findById(gameId);
        isGameEmpty(game);
        isTeamsSizeEqualsToParam(game.get().getTeams().size(), request.getScores().size());
        if (!Objects.equals(game.get().getBetroundId(), betRoundId))
            throw new BadRequestException("Invalid betroundId or gameId");
        for (int i = 0; i < game.get().getTeams().size(); i++) {
            game.get().getTeams().get(i).setScore(request.getScores().get(i));
        }
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betRoundId);
        isBetRoundEmpty(betRound);
        betRound.get().setUpdatedDateTime(LocalDate.now());
        gameRepository.save(game.get());
        betRoundRepository.save(betRound.get());
        return gameToDto(game.get());
    }

/*    public GameEntity changeGameModuleToTurtleGame(Long gameId) {
        Optional<GameEntity> game = gameRepository.findById(gameId);
        isGameEmpty(game);
        teamRepository.saveAll(setGameToTurtle(game.get()));
        game.get().setTeams(setGameToTurtle(game.get()));
        betRoundRepository.save(betRoundRepository.findById(game.get().getBetroundId()).get());
        return gameRepository.save(game.get());
    }*/


    @Override
    @Transactional
    public void deleteBetRound(Long betroundId) {
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        List<UserBetRoundEntity> userbetrounds = userBetRoundRepository.findByBetRoundEntityId(betroundId);
        if (!userbetrounds.isEmpty()) {
            for (int i = 0; i < userbetrounds.size(); i++) {
                deleteUserBetRound(betroundId, userbetrounds.get(i).getId());
            }
        }
        List<GameEntity> games = gameRepository.findAllByBetroundId(betroundId);
        if (!games.isEmpty()) {
            for (int i = 0; i < games.size(); i++) {
                deleteGame(betroundId,games.get(i).getId());
                betRound.get().getGames().remove(games.get(i));
            }
        }
        betRoundRepository.save(betRound.get());
    }

    @Transactional
    @Override
    public void deleteGame(Long betroundId, Long gameId) {
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        Optional<GameEntity> game = gameRepository.findById(gameId);
        isGameEmpty(game);
        if (!game.get().getBetroundId().equals(betRound.get().getId()))
            throw new NotfoundException("Betround id and game id is not matching.");
        betRound.get().getGames().remove(game.get());

        List<UserBetEntity> allBetsForThisGame = userBetRepository.findAllByGameEntityId(gameId);
        for (int i = 0; i < allBetsForThisGame.size(); i++) {
            UserBetEntity bet = allBetsForThisGame.get(i);
            deleteBet(betroundId, bet.getUserBetRoundId(), bet.getId());
        }
        gameRepository.delete(game.get());
        betRoundRepository.save(betRound.get());
    }

    @Transactional
    @Override
    public void deleteUserBetRound(Long betroundId, Long userbetroundId) {
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        Optional<UserBetRoundEntity> userBetRound = userBetRoundRepository.findById(userbetroundId);
        isUserBetRoundEmpty(userBetRound);
        if (userBetRound.get().getBetRoundEntityId() != betRound.get().getId()) throw new NotfoundException("Betround id and Userbetround id is not matching.");

        for (int i = 0; i < userBetRound.get().getUserBetList().size(); i++) {
            UserBetEntity bet = userBetRound.get().getUserBetList().get(i);
            isUserBetEmpty(Optional.ofNullable(bet));
            deleteBet(betroundId, userbetroundId, bet.getId());
            userBetRound.get().getUserBetList().remove(bet);
        }
        userBetRoundRepository.delete(userBetRound.get());


    }

    @Transactional
    public void deleteBet(Long betroundId, Long userbetroundId, String betId) {
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        Optional<UserBetRoundEntity> userBetRound = userBetRoundRepository.findById(userbetroundId);
        isUserBetRoundEmpty(userBetRound);

        Optional<UserBetEntity> bet = userBetRepository.findById(betId);
        isUserBetEmpty(bet);
        if (!(bet.get().getUserBetRoundId() == userbetroundId && userBetRound.get().getBetRoundEntityId() == betroundId)) {
            //eger hersey yolundaysa
            userBetRound.get().getUserBetList().remove(bet.get());
            userBetRepository.delete(bet.get());
            userBetRoundRepository.save(userBetRound.get());
        }


    }
}
