package com.betting.karakoc.service.managers;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.enums.BetStatus;
import com.betting.karakoc.models.real.*;
import com.betting.karakoc.models.requests.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.service.interfaces.AdminService;
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

import static com.betting.karakoc.KarakocApplication.GAME_MAX_COUNT;
import static com.betting.karakoc.models.real.BetRoundEntity.*;
import static com.betting.karakoc.models.real.GameEntity.*;
import static com.betting.karakoc.models.real.UserBetEntity.isUserBetEmpty;
import static com.betting.karakoc.models.real.UserBetRoundEntity.isUserBetRoundEmpty;
import static com.betting.karakoc.models.real.UserEntity.isEmpty;
import static com.betting.karakoc.models.real.UserEntity.onlyAdminValidation;

@Data
@Service
@AllArgsConstructor


public class AdminManager implements AdminService {

    private final UserEntityRepository userRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final BetRoundEntityRepository betRoundRepository;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final UserBetRepository userBetRepository;

    @Transactional
    public GameEntityDTO createGame(CreateGameRequest request) throws InterruptedException {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);


        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);

        //if betround's gamesize lower than GAME_MAX_COUNT; add game. if not throw exception.
        //don't change the second param. its locked.!.!.!
        isBetroundsGameSizeEqualsEnvironmentVariable(betRound.get(), GAME_MAX_COUNT);

        GameEntity game = createGameBuilder(betRound.get(), request);
        gameRepository.save(game);
        teamRepository.saveAll(game.getTeams());
        betRound.get().getGames().add(game);

        // here is a little complicated.
        // if betrounds game size equals to GAME_MAX_COUNT, open for bets and save to repository.
        // if not, don't open for bets and save repository with games.
        //don't change the second param. its locked.!.!.!
        betRoundRepository.save(setPlannedIfGamesSizeIsEnvironmentVariable(betRound.get(), GAME_MAX_COUNT));
        return gameToDto(game);
    }

    public BetRoundEntityDTO createBetRound(CreateBetRoundRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        BetRoundEntity betRound = createBetRoundBuilder(request);
        betRoundRepository.save(betRound);
        return betroundToDto(betRound);

    }

    public Page<UserEntity> getAllUsers(GetAllUsersRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        return userRepository.findAll(PageRequest.of(request.getPageSize(), 20, Sort.Direction.DESC, "createddatetime"));
    }

    public List<BetRoundEntityDTO> getCreatedBetRounds(GetCreatedBetRoundsRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);
        List<BetRoundEntity> createdBetRounds = betRoundRepository.findAllByBetStatus(BetStatus.CREATED);

        return betroundsToDtos(createdBetRounds);
    }


    public BetRoundEntityDTO endBetRound(EndBetRoundRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);
        betRound.get().setBetStatus(BetStatus.ENDED);
        betRoundRepository.save(betRound.get());
        return betroundToDto(betRound.get());
    }

    @Transactional
    public GameEntityDTO putGame(PutGameRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        isEmpty(game);
        isTeamsSizeEqualsToParam(game.get().getTeams().size(), request.getScores().size());
        if (!Objects.equals(game.get().getBetroundId(), request.getBetRoundId()))
            throw new BadRequestException("Invalid betroundId or gameId");
        for (int i = 0; i < game.get().getTeams().size(); i++) {
            game.get().getTeams().get(i).setScore(request.getScores().get(i));
        }
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetRoundId());
        isEmpty(betRound);
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



    @Transactional
    public void deleteBetRound(DeleteBetRoundRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);
        List<UserBetRoundEntity> userbetrounds = userBetRoundRepository.findAllByBetRoundEntityId(request.getBetroundId());
        if (!userbetrounds.isEmpty()) {
            for (int i = 0; i < userbetrounds.size(); i++) {
                deleteUserBetRound(new DeleteUserBetRoundRequest(request.getBetroundId(), userbetrounds.get(i).getId(), request.getAdminToken()));
            }
        }
        List<GameEntity> games = gameRepository.findAllByBetroundId(request.getBetroundId());
        if (!games.isEmpty()) {
            for (int i = 0; i < games.size(); i++) {
                deleteGame(new DeleteGameRequest(request.getBetroundId(), games.get(i).getId(), request.getAdminToken()));
                betRound.get().getGames().remove(games.get(i));
            }
        }
        betRoundRepository.save(betRound.get());
    }

    @Transactional

    public void deleteGame(DeleteGameRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);
        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        isGameEmpty(game);
        if (!game.get().getBetroundId().equals(betRound.get().getId()))
            throw new NotfoundException("Betround id and game id is not matching.");
        betRound.get().getGames().remove(game.get());

        List<UserBetEntity> allBetsForThisGame = userBetRepository.findAllByGameEntityId(request.getGameId());
        for (int i = 0; i < allBetsForThisGame.size(); i++) {
            UserBetEntity bet = allBetsForThisGame.get(i);
            deleteBet(new DeleteBetRequest(request.getBetroundId(), bet.getUserBetRoundId(), bet.getId(), request.getAdminToken()));
        }
        gameRepository.delete(game.get());
        betRoundRepository.save(betRound.get());
    }

    @Transactional
    public void deleteUserBetRound(DeleteUserBetRoundRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);
        Optional<UserBetRoundEntity> userBetRound = userBetRoundRepository.findById(request.getUserbetroundId());
        isUserBetRoundEmpty(userBetRound);
        if (userBetRound.get().getBetRoundEntityId() != betRound.get().getId()) throw new NotfoundException("Betround id and Userbetround id is not matching.");

        for (int i = 0; i < userBetRound.get().getUserBetList().size(); i++) {
            UserBetEntity bet = userBetRound.get().getUserBetList().get(i);
            isUserBetEmpty(Optional.ofNullable(bet));
            deleteBet(new DeleteBetRequest(request.getBetroundId(), request.getUserbetroundId(), bet.getId(), request.getAdminToken()));
            userBetRound.get().getUserBetList().remove(bet);
        }
        userBetRoundRepository.delete(userBetRound.get());


    }

    @Transactional
    public void deleteBet(DeleteBetRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(request.getBetroundId());
        isBetRoundEmpty(betRound);
        Optional<UserBetRoundEntity> userBetRound = userBetRoundRepository.findById(request.getUserbetroundId());
        isUserBetRoundEmpty(userBetRound);

        Optional<UserBetEntity> bet = userBetRepository.findById(request.getBetId());
        isUserBetEmpty(bet);
        if (!(bet.get().getUserBetRoundId() == request.getBetroundId() && userBetRound.get().getBetRoundEntityId() == request.getBetroundId())) {
            //eger hersey yolundaysa
            userBetRound.get().getUserBetList().remove(bet.get());
            userBetRepository.delete(bet.get());
            userBetRoundRepository.save(userBetRound.get());
        }


    }
}
