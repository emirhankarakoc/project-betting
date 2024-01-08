package com.betting.karakoc.service.manager;

import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.service.repo.BetSummaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.model.real.BetRoundEntity.*;
import static com.betting.karakoc.model.real.UserBetEntity.userBetToDto;
import static com.betting.karakoc.model.real.UserBetEntity.userBetValidation;
import static com.betting.karakoc.model.real.UserBetRoundEntity.isUserBetRoundEmptyAndisUserPlayedForThisBetround;
import static com.betting.karakoc.model.real.UserEntity.isUserEmpty;

@Data
@Service
@AllArgsConstructor
public class BetSummaryManager implements BetSummaryService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;

    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;



    public List<UserBetEntityDTO> getAllBetsByGame(long betRoundId){
        List<UserBetEntity> usersBetList = userBetRepository.findAll();
        List<UserBetEntityDTO> responseAllBets = new ArrayList<>();
        for (UserBetEntity bet: usersBetList) {
            if ((bet.getUserBetRoundId())==betRoundId){
                responseAllBets.add(userBetToDto(bet));
            }
        }
        return responseAllBets;
    }

    public String summaryBets(Long userBetRoundId,String token){

        Optional<UserEntity> user = userRepository.findByToken(token);
        isUserEmpty(user);
        Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findByIdAndUserEntityId(userBetRoundId,user.get().getId());
        isUserBetRoundEmptyAndisUserPlayedForThisBetround(userbetround,user);
        Optional<BetRoundEntity> tokendenGelenUserinOynadigiUserBetRoundunBetRoundu = betRepository.findById(userbetround.get().getBetRoundEntityId());
        isBetRoundEmpty(tokendenGelenUserinOynadigiUserBetRoundunBetRoundu);
        isBetroundEnded(tokendenGelenUserinOynadigiUserBetRoundunBetRoundu);
        List<UserBetEntity> tokendenGelenKullanicininOynadigiUserBetRounddakiBetler = userBetRepository.findAllByUserBetRoundId(userbetround.get().getId());
        userBetValidation(tokendenGelenKullanicininOynadigiUserBetRounddakiBetler);
        userbetround.get().setCorrectGuessedMatchCount(0);
        UserBetEntity sectigi = null;
        int correctsCount = 0;
        List<GameEntity> gamesList = tokendenGelenUserinOynadigiUserBetRoundunBetRoundu.get().getGames();
        for ( int i = 0;i<13;i++){
             sectigi = tokendenGelenKullanicininOynadigiUserBetRounddakiBetler.get(i);
            if(sectigi.getSelection()== Selection.FIRST && gamesList.get(i).getScoreFirstTeam()>gamesList.get(i).getScoreSecondTeam()||sectigi.getSelection()== Selection.SECOND && gamesList.get(i).getScoreFirstTeam()<gamesList.get(i).getScoreSecondTeam()||sectigi.getSelection()== Selection.DRAW && gamesList.get(i).getScoreFirstTeam()==gamesList.get(i).getScoreSecondTeam()){
                sectigi.setIsGuessCorrect(true);
                correctsCount++;
            }
            else sectigi.setIsGuessCorrect(false);
        }
        userbetround.get().setCorrectGuessedMatchCount(correctsCount);
        userBetRepository.save(sectigi);
        userBetRoundRepository.save(userbetround.get());
        AllInOneEntity all = new AllInOneEntity();
        all.setBetRound(tokendenGelenUserinOynadigiUserBetRoundunBetRoundu.get());
        all.setUserBetRound(userbetround.get());
        all.setUser(user.get());
        all.setMesaj("13 tane oyundan, "+all.getUserBetRound().getCorrectGuessedMatchCount() + " tanesini dogru bildiniz. Tebrikler "+ all.getUser().getFirstname() + "...");
        return all.getMesaj();
    }


































}
