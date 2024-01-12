package com.betting.karakoc.service.manager;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;

import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.service.repo.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.betting.karakoc.model.real.BetRoundEntity.*;
import static com.betting.karakoc.model.real.GameEntity.isGameEmpty;
import static com.betting.karakoc.model.real.GameEntity.setGameToTurtle;
import static com.betting.karakoc.model.real.Team.isTeamEmpty;
import static com.betting.karakoc.model.real.UserBetEntity.isUserBetIsPresent;
import static com.betting.karakoc.model.real.UserBetEntity.userBetToDto;
import static com.betting.karakoc.model.real.UserBetRoundEntity.*;
import static com.betting.karakoc.model.real.UserEntity.*;

@Data
@Service

@AllArgsConstructor


public class UserManager implements UserService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;
private final JavaMailSender mailSender;
    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;
    private final SecurityContextUtil securityContextUtil;
    private final TeamRepository teamRepository;

        public List<BetRoundEntityDTO> getPlannedBetRounds(){
        List<BetRoundEntity> betrounds = betRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();
        for (BetRoundEntity betRound:betrounds){
            if (betRound.getBetStatus()==BetStatus.PLANNED){
                responseList.add(betroundToDto(betRound));}
        }
            return responseList;
        }


        public List<BetRoundEntityDTO> getEndedBetRounds(){
            List<BetRoundEntity> betrounds = betRepository.findAll();
            List<BetRoundEntityDTO> responseList = new ArrayList<>();
            for (BetRoundEntity betRound:betrounds){
                if (betRound.getBetStatus()==BetStatus.ENDED){
                    responseList.add(betroundToDto(betRound));}
            }
            return responseList;

        }

        public UserBetRoundEntityDTO createUserBetRound(Long betRoundEntityId){

            Optional<BetRoundEntity> kontrol = betRepository.findById(betRoundEntityId);
            isBetRoundEmpty(kontrol);
            isBetRoundsGameIsNotXX(kontrol.get());            //BOYLE BİR TOKEN YOKSA ERROR VERİLMELİDİR.
           UserBetRoundEntity userbet =  userBetRoundRepository.save(createUserbetRoundBuilder(betRoundEntityId,securityContextUtil.getCurrentUser()));
            return userBetRoundToDto(userbet);
        }


        @Transactional
        public UserBetEntityDTO creteUserBet(Long userBetRoundId, Long gameId, Long betTeamId){

            UserEntity user = securityContextUtil.getCurrentUser();
            Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findById(userBetRoundId);
            isUserBetRoundIsEmpty(userbetround);
            Optional<BetRoundEntity> betRoundEntity = betRepository.findById(userbetround.get().getBetRoundEntityId());
            isBetRoundEmpty(betRoundEntity);
            Optional<GameEntity> game = gameRepository.findById(gameId);
            isGameEmpty(game);
            Optional<Team> team = teamRepository.findById(betTeamId);
            isTeamEmpty(team);

        if (game.get().getBetroundId() != userbetround.get().getBetRoundEntityId()) throw new GeneralException("Wrong game selected.",400);
        if (!game.get().getTeams().contains(team.get())) throw  new GeneralException("This game does not contain given betTeamId.",400);


        List<UserBetEntity> userBetlistesi = userbetround.get().getUserBetList();
        List<UserBetEntity> yeniListe = new ArrayList<>();
            for (UserBetEntity bet: userBetlistesi) {
                if (bet.getGameEntityId().equals(gameId)) yeniListe.add(bet);
            }

        Optional<UserBetEntity> userBetControl = userBetRepository.findByGameEntityIdAndUserBetRoundId(gameId,userBetRoundId);
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
            String oynayanTakimlar = "-";
            for (int i = 0;i<game.get().getTeams().size();i++){
                oynayanTakimlar+= game.get().getTeams().get(i).getName() + "-";
            }
            dto.setOynayanTakimlar(oynayanTakimlar+", Oynadiginiz takim : "+ teamRepository.findById(betTeamId).get().getName());
            return dto;




        }



        public BetRoundEntityDTO getBetroundById(Long id){
        Optional<BetRoundEntity> betRound = betRepository.findById(id);
        isBetRoundEmpty(betRound);
        return betroundToDto(betRound.get());

        }

        public UserEntityDTO changePassword(String username, String password, String newPassword){
            UserEntity user = securityContextUtil.getCurrentUser();

            if (!user.getPassword().equals(password)) throw new GeneralException("Username or password is not matching.",400);
            user.setPassword(newPassword);
            userRepository.save(user);
            UserEntityDTO dto =  userToDto(user);
            dto.setMessage("Password changed. Good bettings!!!");
            return dto;

        }








}
