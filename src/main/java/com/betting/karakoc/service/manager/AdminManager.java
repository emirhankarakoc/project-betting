package com.betting.karakoc.service.manager;

import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Data
@Service
@AllArgsConstructor


public class AdminManager implements AdminService {

    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRoundRepository;

    private final GameRepository gameRepository;

    @Transactional
    public GameEntityDTO createGame(Long betroundId,CreateGameRequest request,String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Access denied.",401);
        if (user.get().getRole()!= UserRole.ROLE_ADMIN) throw new GeneralException("Forbidden.",403);

        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        if (betRound.isEmpty()) throw new GeneralException("There is not a betround with this id, check one more time.",401);
        if (betRound.get().getGames().size()==13) throw new GeneralException("Cant add, game count must be 13.",400);

        GameEntity game = new GameEntity();

        game.setBetroundId(betroundId);
        game.setFirstTeam(request.getFirstTeam());
        game.setScoreFirstTeam(0);
        game.setSecondTeam(request.getSecondTeam());
        game.setScoreSecondTeam(0);
        gameRepository.save(game);
        betRound.get().getGames().add(game);
        betRoundRepository.save(betRound.get());
        if (betRound.get().getGames().size()==13){
        betRound.get().setBetStatus(BetStatus.PLANNED);
        }

        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(game.getId());
        dto.setBetroundId(game.getBetroundId());
        dto.setFirstTeam(game.getFirstTeam());
        dto.setSecondTeam(game.getSecondTeam());
        dto.setScoreFirstTeam(game.getScoreFirstTeam());
        dto.setScoreSecondTeam(game.getScoreSecondTeam());
        return dto;
    }


    public BetRoundEntityDTO createBetRound(CreateBetRoundRequest request, String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Access denied.",401);
        if (user.get().getRole()!= UserRole.ROLE_ADMIN) throw new GeneralException("Forbidden.",403);

        if (request.getPlayDateTime().isBefore(LocalDateTime.now())) throw new GeneralException("You cant add a betround which have past playdate.",400);



        BetRoundEntity betRound = new BetRoundEntity();
        betRound.setTitle(request.getTitle());
        betRound.setBetStatus(BetStatus.CREATED);
        betRound.setCreatedDateTime(LocalDate.now());
        betRound.setUpdatedDateTime(LocalDate.now());
        betRound.setPlayDateTime(request.getPlayDateTime());
        betRound.setGames(null);
        betRoundRepository.save(betRound);

        BetRoundEntityDTO dto = new BetRoundEntityDTO();
        dto.setId(betRound.getId());
        dto.setTitle(betRound.getTitle());
        dto.setStatus(betRound.getBetStatus());
        dto.setCreatedDateTime(betRound.getCreatedDateTime());
        dto.setUpdatedDateTime(betRound.getUpdatedDateTime());
        dto.setPlayDateTime(betRound.getPlayDateTime());
        dto.setGames(betRound.getGames());
        return dto;
    }
    public Page<UserEntity> getAllUsers(String token, int pageNumber){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Invalid token.",400);
        if (!(user.get().getRole()==UserRole.ROLE_ADMIN)) throw new GeneralException("Forbidden.",403);


        Page<UserEntity> usersWithPagination = userRepository.findAll(PageRequest.of(pageNumber,20, Sort.Direction.DESC,"createddatetime"));

        return usersWithPagination;

    }
    public List<BetRoundEntityDTO> getCreatedBetRounds(String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Invalid token.",400);
        if (user.get().getRole()!=UserRole.ROLE_ADMIN) throw new GeneralException("Forbidden.",403);




        List<BetRoundEntity> betrounds = betRoundRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();

        for (BetRoundEntity betRound:betrounds){
            if (betRound.getBetStatus()==BetStatus.CREATED){
                BetRoundEntityDTO dto = new BetRoundEntityDTO();
                dto.setId(betRound.getId());
                dto.setTitle(betRound.getTitle());
                dto.setStatus(betRound.getBetStatus());
                dto.setGames(betRound.getGames());
                dto.setCreatedDateTime(betRound.getUpdatedDateTime());
                dto.setUpdatedDateTime(betRound.getUpdatedDateTime());
                dto.setPlayDateTime(betRound.getPlayDateTime());
                responseList.add(dto);}
        }
        return responseList;
    }


    public BetRoundEntityDTO endBetRound(Long betroundId, String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw  new GeneralException("Invalid token.",400);
        if (user.get().getRole()!= UserRole.ROLE_ADMIN) throw new GeneralException("Forbidden.",403);


        Optional<BetRoundEntity> betRound = betRoundRepository.findById(betroundId);
        if (betRound.isEmpty()) throw new GeneralException("Invalid betroundId",400);

        betRound.get().setBetStatus(BetStatus.ENDED);
        betRoundRepository.save(betRound.get());

        BetRoundEntityDTO dto = new BetRoundEntityDTO();
        dto.setId(betRound.get().getId());
        dto.setTitle(betRound.get().getTitle());
        dto.setStatus(betRound.get().getBetStatus());
        dto.setCreatedDateTime(betRound.get().getCreatedDateTime());
        dto.setUpdatedDateTime(betRound.get().getUpdatedDateTime());
        dto.setPlayDateTime(betRound.get().getPlayDateTime());
        dto.setGames(betRound.get().getGames());
        return dto;
    }


    public GameEntityDTO putGame(PutGameRequest request,String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Invalid token",400);
        if (user.get().getRole()!= UserRole.ROLE_ADMIN) throw new GeneralException("Forbidden.",403);



        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        if (game.isEmpty()) throw new GeneralException("Wrong game Id.",400);


        game.get().setScoreFirstTeam(request.getScoreFirstTeam());
        game.get().setScoreSecondTeam(request.getScoreSecondTeam());

        BetRoundEntity betRound = betRoundRepository.findById(game.get().getBetroundId()).get();
        betRound.setUpdatedDateTime(LocalDate.now());
        gameRepository.save(game.get());
        betRoundRepository.save(betRound);

        GameEntityDTO dto = new GameEntityDTO();
        dto.setId(game.get().getId());
        dto.setBetroundId(game.get().getBetroundId());
        dto.setFirstTeam(game.get().getFirstTeam());
        dto.setSecondTeam(game.get().getSecondTeam());
        dto.setScoreFirstTeam(request.getScoreFirstTeam());
        dto.setScoreSecondTeam(request.getScoreSecondTeam());

        return dto;












    }










}
