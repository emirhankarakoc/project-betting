package com.betting.karakoc.service.manager;

import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.enums.UserRole;
import com.betting.karakoc.model.real.BetRoundEntity;
import com.betting.karakoc.model.real.GameEntity;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.PutGameRequest;
import com.betting.karakoc.repository.BetRoundEntityRepository;
import com.betting.karakoc.repository.GameRepository;
import com.betting.karakoc.repository.UserEntityRepository;
import com.betting.karakoc.service.repo.AdminService;
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
import java.util.Optional;

import static com.betting.karakoc.model.real.BetRoundEntity.*;
import static com.betting.karakoc.model.real.GameEntity.*;
import static com.betting.karakoc.model.real.UserEntity.isUserEmpty;
import static com.betting.karakoc.model.real.UserEntity.isUserIsAdmin;

@Data
@Service
@AllArgsConstructor


public class AdminManager implements AdminService {

    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRoundRepository;

    private final GameRepository gameRepository;

    @Transactional
    public GameEntityDTO createGame(Long betroundId, CreateGameRequest request, String token){

        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        isBetroundsGameSizeLowerThan13(betRound.get());
        GameEntity game = createGameBuilder(betRound,request);
        gameRepository.save(game);
        betRound.get().getGames().add(game);
        betRoundRepository.save(betRound.get());
        setPlannedIfGamesSizeIs13(betRound.get());
       return gameToDto(game);
    }


    public BetRoundEntityDTO createBetRound(CreateBetRoundRequest request, String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        isPlayDatePast(request.getPlayDateTime());
        BetRoundEntity betRound = createBetRoundBuilder(request);
        betRoundRepository.save(betRound);
       return betroundToDto(betRound);

    }
    public Page<UserEntity> getAllUsers(String token, int pageNumber){
        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        return userRepository.findAll(PageRequest.of(pageNumber,20, Sort.Direction.DESC,"createddatetime"));


    }
    public List<BetRoundEntityDTO> getCreatedBetRounds(String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        List<BetRoundEntity> betrounds = betRoundRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();
        for (BetRoundEntity betRound:betrounds){
            if (betRound.getBetStatus()==BetStatus.CREATED){
                responseList.add(betroundToDto(betRound));}
        }
        return responseList;
    }


    public BetRoundEntityDTO endBetRound(Long betroundId, String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        betRound.get().setBetStatus(BetStatus.ENDED);
        betRoundRepository.save(betRound.get());
        return betroundToDto(betRound.get());
    }


    public GameEntityDTO putGame(PutGameRequest request,String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        isUserIsAdmin(user);
        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        isGameEmpty(game);
        game.get().setScoreFirstTeam(request.getScoreFirstTeam());
        game.get().setScoreSecondTeam(request.getScoreSecondTeam());
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(game.get().getBetroundId());
        isBetRoundEmpty(betRound);
        betRound.get().setUpdatedDateTime(LocalDate.now());
        gameRepository.save(game.get());
        betRoundRepository.save(betRound.get());
        return gameToDto(game.get());
    }










}
