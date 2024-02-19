package com.betting.karakoc.service.managers;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.BetStatus;
import com.betting.karakoc.models.real.*;
import com.betting.karakoc.models.requests.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.betting.karakoc.models.real.BetRoundEntity.*;
import static com.betting.karakoc.models.real.GameEntity.isGameEmpty;
import static com.betting.karakoc.models.real.Team.isTeamEmpty;
import static com.betting.karakoc.models.real.UserBetEntity.isUserBetIsPresent;
import static com.betting.karakoc.models.real.UserBetEntity.userBetToDto;
import static com.betting.karakoc.models.real.UserBetRoundEntity.*;
import static com.betting.karakoc.models.real.UserEntity.*;

@Data
@Service

@AllArgsConstructor


public class UserManager implements UserService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betroundRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final JavaMailSender mailSender;
    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public List<BetRoundEntityDTO> getPlannedBetRounds(GetPlannedBetRoundsRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getUserToken());
        // If token is not valid token, throw exception. if not, welcome. keep continue please
        realUserValidation(user);

//        List<BetRoundEntity> betrounds = betRepository.findAll();
//        List<BetRoundEntityDTO> responseList = new ArrayList<>();
//        for (BetRoundEntity betRound : betrounds) {
//            if (betRound.getBetStatus() == BetStatus.PLANNED) {
//                responseList.add(betroundToDto(betRound));
//            }
//        }

        List<BetRoundEntity> plannedBetrounds = betroundRepository.findAllByBetStatus(BetStatus.PLANNED);
        return betroundsToDtos(plannedBetrounds);
    }


    public List<BetRoundEntityDTO> getEndedBetRounds(GetEndedBetRoundsRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getUserToken());
        // If token is not valid token, throw exception. if not, welcome. keep continue please
        realUserValidation(user);

//        List<BetRoundEntity> betrounds = betRepository.findAll();
//        List<BetRoundEntityDTO> responseList = new ArrayList<>();
//        for (BetRoundEntity betRound : betrounds) {
//            if (betRound.getBetStatus() == BetStatus.ENDED) {
//                responseList.add(betroundToDto(betRound));
//            }
//        }
        List<BetRoundEntity> endedBetrounds = betroundRepository.findAllByBetStatus(BetStatus.ENDED);
        return betroundsToDtos(endedBetrounds);


    }
@Transactional
    public UserBetRoundEntityDTO createUserBetRound(CreateUserBetRoundRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getUserToken());
        // If token is not valid token, throw exception. if not, welcome. keep continue please
        realUserValidation(user);

        Optional<BetRoundEntity> betround = betroundRepository.findById(request.getBetRoundEntityId());
        isBetRoundEmpty(betround);
        isBetRoundsGameIsNotXX(betround.get());
        isBetroundStatusCreatedOrEnded(betround.get());

        UserBetRoundEntity userbet = userBetRoundRepository.save(createUserbetRound(request.getBetRoundEntityId(), userRepository.findByToken(request.getUserToken()).get() ));
        return userBetRoundToDto(userbet);
    }


    @Transactional
    public UserBetEntityDTO creteUserBet(CreateBetRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getUserToken());
        // If token is not valid token, throw exception. if not, welcome. keep continue please
        realUserValidation(user);

        // Actually I don't want this but I have to... for you...
        String betRoundId = request.getBetRoundId();
        String userBetRoundId = request.getUserBetRoundId();
        String gameId = request.getGameId();
         int betTeamId = request.getBetTeamId();


        Optional<BetRoundEntity> betRoundEntity = betroundRepository.findById(betRoundId);
        isBetRoundEmpty(betRoundEntity);
        isBetroundStatusCreatedOrEnded(betRoundEntity.get());

        Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findById(userBetRoundId);
        isUserBetRoundEmpty(userbetround);
        if (!Objects.equals(userbetround.get().getUserToken(), request.getUserToken())) throw new BadRequestException("You are not this userbetround owner.");

        Optional<GameEntity> game = gameRepository.findById(gameId);
        isGameEmpty(game);
        Optional<Team> team = teamRepository.findById(betTeamId);
        isTeamEmpty(team);

        if (!Objects.equals(game.get().getBetroundId(), userbetround.get().getBetRoundEntityId()) && !Objects.equals(userbetround.get().getBetRoundEntityId(), betRoundId))
            throw new BadRequestException("Wrong game selected.");
        if (!game.get().getTeams().contains(team.get()))
            throw new BadRequestException("This game does not contain given betTeamId.");


        List<UserBetEntity> userBetlistesi = userbetround.get().getUserBetList();
        List<UserBetEntity> newUserBetList = new ArrayList<>();
        for (UserBetEntity bet : userBetlistesi) {
            if (bet.getGameEntityId().equals(gameId)) newUserBetList.add(bet);
        }

        Optional<UserBetEntity> userBetControl = userBetRepository.findByGameEntityIdAndUserBetRoundId(gameId, userBetRoundId);
        isUserBetIsPresent(userBetControl);

        UserBetEntity userBet = new UserBetEntity();
        userBet.setUserBetRoundId(userBetRoundId);
        userBet.setGameEntityId(gameId);
        userBet.setBetTeamId(betTeamId);
        userbetround.get().getUserBetList().add(userBet);
        userBetRepository.save(userBet);
        userBetRoundRepository.save(userbetround.get());
        //dtolama
        UserBetEntityDTO dto = userBetToDto(userBet);
        StringBuilder playerTeams = new StringBuilder();
        for (int i = 0; i < game.get().getTeams().size(); i++) {
            playerTeams.append(game.get().getTeams().get(i).getName()).append("-");
        }
        dto.setTeams(playerTeams.toString() + ", Your bet : " + teamRepository.findById(betTeamId).get().getName());
        return dto;


    }


    public BetRoundEntityDTO getBetroundById( String id) {
        Optional<BetRoundEntity> betRound = betroundRepository.findById(id);
        isBetRoundEmpty(betRound);
        return betroundToDto(betRound.get());

    }

    public UserEntityDTO changePassword(ChangePasswordRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getUserToken());
        // If token is not valid token, throw exception. if not, welcome. keep continue please
        realUserValidation(user);

        if (!user.get().getPassword().equals(request.getPassword()))
            throw new BadRequestException("mail or password is not matching.");
        user.get().setPassword(request.getPassword());
        userRepository.save(user.get());
        UserEntityDTO dto = userToDto(user.get());
        dto.setMessage("Password changed. Good bettings!!!");
        return dto;

    }


}
