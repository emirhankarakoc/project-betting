package com.betting.karakoc.service.manager;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.enums.UserRole;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;

import com.betting.karakoc.service.repo.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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

        public List<BetRoundEntityDTO> getPlannedBetRounds(){
        List<BetRoundEntity> betrounds = betRepository.findAll();
        List<BetRoundEntityDTO> responseList = new ArrayList<>();

        for (BetRoundEntity betRound:betrounds){
            if (betRound.getBetStatus()==BetStatus.PLANNED){
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


        public List<BetRoundEntityDTO> getEndedBetRounds(){
            List<BetRoundEntity> betrounds = betRepository.findAll();
            List<BetRoundEntityDTO> responseList = new ArrayList<>();
            for (BetRoundEntity betRound:betrounds){
                if (betRound.getBetStatus()==BetStatus.ENDED){
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

        public UserBetRoundEntityDTO createUserBetRound(Long betRoundEntityId, String token){
            Optional<BetRoundEntity> kontrol = betRepository.findById(betRoundEntityId);
            if (kontrol.isEmpty()) throw new GeneralException("Invalid Bet Round Id.",400);
            if (kontrol.get().getGames().size()!=13) throw new GeneralException("You cant create userbetround for this betround, betrounds games isnt enough now: "+kontrol.get().getGames().size()+"must be 13",400);
            //BOYLE BİR TOKEN YOKSA ERROR VERİLMELİDİR.
            Optional<UserEntity> user = userRepository.findByToken(token);
            if (user.isEmpty()) throw new GeneralException("Invalid token.",400);
            if (!(user.get().getRole()== UserRole.ROLE_USER || user.get().getRole()==UserRole.ROLE_ADMIN)) throw new GeneralException("Access denied.",401);

            UserBetRoundEntity userBetRound = new UserBetRoundEntity();
            userBetRound.setCreatedDateTime(LocalDate.now());
            userBetRound.setUpdatedDateTime(LocalDate.now());
            userBetRound.setUserEntityId((userRepository.findByToken(token).get()).getId());
            userBetRound.setBetRoundEntityId(betRoundEntityId);
            userBetRound.setUserBetList(null);
            userBetRound.setCorrectGuessedMatchCount(0);

            userBetRoundRepository.save(userBetRound);

            UserBetRoundEntityDTO dto = new UserBetRoundEntityDTO();
            dto.setId(userBetRound.getId());
            dto.setCreatedDateTime(userBetRound.getCreatedDateTime());
            dto.setUpdatedDateTime(userBetRound.getUpdatedDateTime());
            dto.setUserEntityId(userBetRound.getUserEntityId());
            dto.setBetRoundEntityId(userBetRound.getBetRoundEntityId());
            dto.setUserBetList(userBetRound.getUserBetList());
            dto.setCorrectGuessedMatchCount(userBetRound.getCorrectGuessedMatchCount());
            return dto;
        }


        @Transactional
        public UserBetEntityDTO creteUserBet(Long userBetRoundId, Long gameId, Selection selection, String token){
            Optional<UserEntity> user = userRepository.findByToken(token);
            if (user.isEmpty()) throw new GeneralException("Invalid token.",400);

        Optional<UserBetRoundEntity> betRound = userBetRoundRepository.findById(userBetRoundId);
            if (betRound.isEmpty()) throw new GeneralException("Invalid Bet Round Id.",400);

            Optional<BetRoundEntity> betRoundEntity = betRepository.findById(betRound.get().getBetRoundEntityId());
            if (betRoundEntity.isEmpty()) throw new GeneralException("Invalid Bet Round Id.",400);
        Optional<GameEntity> game = gameRepository.findById(gameId);
        if (game.isEmpty()) throw new GeneralException("Invalid Game Id.",400);
            if (betRoundEntity.get().getBetStatus()==BetStatus.ENDED || betRoundEntity.get().getBetStatus()==BetStatus.CREATED) throw new GeneralException("You cant access this betround at this moment.",400);

        if (game.get().getBetroundId() != betRound.get().getBetRoundEntityId()) throw new GeneralException("Wrong game selected.",400);


        List<UserBetEntity> userBetlistesi = betRound.get().getUserBetList();
        List<UserBetEntity> yeniListe = new ArrayList<>();
            for (UserBetEntity bet: userBetlistesi) {
                if (bet.getGameEntityId()==gameId) yeniListe.add(bet);
            }

        Optional<UserBetEntity> userBetControl = userBetRepository.findByGameEntityIdAndUserBetRoundId(gameId,userBetRoundId);
        if (userBetControl.isPresent()) throw new GeneralException("You already put your bet.",400);

            UserBetEntity userBet = new UserBetEntity();
            userBet.setUserBetRoundId(userBetRoundId);
            userBet.setGameEntityId(gameId);
            userBet.setSelection(selection);
            betRound.get().getUserBetList().add(userBet);
            userBetRepository.save(userBet);
            userBetRoundRepository.save(betRound.get());
            //dtolama
            UserBetEntityDTO dto = new UserBetEntityDTO();
            dto.setId(userBet.getId());
            dto.setUserBetRoundId(userBet.getUserBetRoundId());
            dto.setGameEntityId(userBet.getGameEntityId());
            dto.setSelection(userBet.getSelection());
            dto.setOynayanTakimlar(""+game.get().getFirstTeam()+" VS "+game.get().getSecondTeam());
            return dto;




        }



        public BetRoundEntityDTO getBetroundById(Long id){
        Optional<BetRoundEntity> betRound = betRepository.findById(id);
        if (!betRound.isPresent()) throw new GeneralException("There is not a betround with this ID.",400);
        BetRoundEntityDTO dto = new BetRoundEntityDTO();
        dto.setId(betRound.get().getId());
        dto.setGames(betRound.get().getGames());
        dto.setStatus(betRound.get().getBetStatus());
        dto.setTitle(betRound.get().getTitle());
        dto.setCreatedDateTime(betRound.get().getCreatedDateTime());
        dto.setUpdatedDateTime(betRound.get().getUpdatedDateTime());
        dto.setPlayDateTime(betRound.get().getPlayDateTime());
        return dto;

        }

        public UserEntityDTO changePassword(String username, String password, String newPassword){











            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> {throw new GeneralException("User not found.",400);});









            if (!user.getPassword().equals(password)) throw new GeneralException("Username or password is not matching.",400);
            user.setPassword(newPassword);
            userRepository.save(user);

            UserEntityDTO dto = new UserEntityDTO();
            dto.setToken(user.getToken());
            dto.setLastname(user.getLastname());
            dto.setPassword(user.getPassword());
            dto.setFirstname(user.getFirstname());
            dto.setUsername(user.getUsername());
            dto.setMessage("Password changed. Good bettings!!!");

            return dto;

        }








}
