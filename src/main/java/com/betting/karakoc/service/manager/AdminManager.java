package com.betting.karakoc.service.manager;

import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.real.BetRoundEntity;
import com.betting.karakoc.model.real.GameEntity;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequestWithTwoTeams;
import com.betting.karakoc.repository.BetRoundEntityRepository;
import com.betting.karakoc.repository.GameRepository;
import com.betting.karakoc.repository.TeamRepository;
import com.betting.karakoc.repository.UserEntityRepository;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.security.annotations.OnlyAdmin;
import com.betting.karakoc.security.annotations.OnlyUser;
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
@Data
@Service
@AllArgsConstructor


public class AdminManager implements AdminService {

    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRoundRepository;
    private final SecurityContextUtil securityContextUtil;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public GameEntityDTO createGame(Long betroundId, CreateGameRequest request, int teamsSize){
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        isBetroundsGameSizeLowerThanXX(betRound.get());

        GameEntity game = createGameBuilder(betRound.get(),request,teamsSize);
        gameRepository.save(game);
        teamRepository.saveAll(game.getTeams());
        betRound.get().getGames().add(game);
        betRoundRepository.save(betRound.get());
        setPlannedIfGamesSizeIsXX(betRound.get());
       return gameToDtoWithTwoTeams(game);
    }

    public BetRoundEntityDTO createBetRound(CreateBetRoundRequest request){

        isPlayDatePast(request.getPlayDateTime());
        BetRoundEntity betRound = createBetRoundBuilder(request);
        betRoundRepository.save(betRound);
       return betroundToDto(betRound);

    }
    public Page<UserEntity> getAllUsers(int pageNumber){
        return userRepository.findAll(PageRequest.of(pageNumber,20, Sort.Direction.DESC,"createddatetime"));
    }
    public List<BetRoundEntityDTO> getCreatedBetRounds(){
        List<BetRoundEntity> betrounds = betRoundRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();
        for (BetRoundEntity betRound:betrounds){
            if (betRound.getBetStatus()==BetStatus.CREATED){

                responseList.add(betroundToDto(betRound));



            }
        }
        return responseList;
    }


    public BetRoundEntityDTO endBetRound(Long betroundId){

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        isBetRoundEmpty(betRound);
        betRound.get().setBetStatus(BetStatus.ENDED);
        betRoundRepository.save(betRound.get());
        return betroundToDto(betRound.get());
    }

    @Transactional
    public GameEntityDTO putGame(PutGameRequestWithTwoTeams request){

        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        isGameEmpty(game);
        isTeamsSizeEqualsToParam(game.get().getTeams().size(),request.getScores().size());
        for (int i = 0;i<game.get().getTeams().size();i++){
            game.get().getTeams().get(i).setScore(request.getScores().get(i));
        }
        Optional<BetRoundEntity> betRound = betRoundRepository.findById(game.get().getBetroundId());
        isBetRoundEmpty(betRound);
        betRound.get().setUpdatedDateTime(LocalDate.now());
        gameRepository.save(game.get());
        betRoundRepository.save(betRound.get());
        return gameToDtoWithTwoTeams(game.get());
    }

    public GameEntity changeGameToModule(Long gameId){
        Optional<GameEntity> game = gameRepository.findById(gameId);
        isGameEmpty(game);
        teamRepository.saveAll(setGameToTurtle(game.get()));
        game.get().setTeams(setGameToTurtle(game.get()));
        betRoundRepository.save(betRoundRepository.findById(game.get().getBetroundId()).get());
        return gameRepository.save(game.get());
    }








}
