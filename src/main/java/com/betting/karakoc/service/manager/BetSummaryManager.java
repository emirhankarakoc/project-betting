package com.betting.karakoc.service.manager;

import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.service.repo.BetSummaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.KarakocApplication.gameMaxCount;
import static com.betting.karakoc.model.real.BetRoundEntity.*;
import static com.betting.karakoc.model.real.GameEntity.isTeamsSizeEqualsToParam;
import static com.betting.karakoc.model.real.GameEntity.oyunSonucuHesapla;
import static com.betting.karakoc.model.real.UserBetEntity.userBetToDto;
import static com.betting.karakoc.model.real.UserBetEntity.userBetValidation;
import static com.betting.karakoc.model.real.UserBetRoundEntity.isUserBetRoundEmptyAndisUserPlayedForThisBetround;


@Data
@Service
@AllArgsConstructor
public class BetSummaryManager implements BetSummaryService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;

    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextUtil securityContextUtil;




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

   /* public String summaryBetsForTwoTeams(Long userBetRoundId){
        UserEntity user = securityContextUtil.getCurrentUser();


        Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findByIdAndUserEntityId(userBetRoundId,user.getId());
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
        isTeamsSizeEqualsToParam(gamesList.size(),2);
        for ( int i = 0;i<13;i++){
             sectigi = tokendenGelenKullanicininOynadigiUserBetRounddakiBetler.get(i);
            if(sectigi.getBetTeamId()== Selection.FIRST && gamesList.get(i).getTeams().get(0).getScore()>gamesList.get(i).getTeams().get(1).getScore() ||sectigi.getSelection()== Selection.SECOND && gamesList.get(i).getTeams().get(0).getScore()<gamesList.get(i).getTeams().get(1).getScore()||sectigi.getSelection()== Selection.DRAW && gamesList.get(i).getTeams().get(0).getScore()==gamesList.get(i).getTeams().get(1).getScore()){
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
        all.setUser(user);
        all.setMesaj("13 tane oyundan, "+all.getUserBetRound().getCorrectGuessedMatchCount() + " tanesini dogru bildiniz. Tebrikler "+ all.getUser().getFirstname() + "...");
        return all.getMesaj();
    }*/

    public String hesapla(Long userBetRoundId){
        UserEntity user = securityContextUtil.getCurrentUser();
        Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findByIdAndUserEntityId(userBetRoundId,user.getId());
        isUserBetRoundEmptyAndisUserPlayedForThisBetround(userbetround,user);
        Optional<BetRoundEntity> betround = betRepository.findById(userbetround.get().getBetRoundEntityId());
        isBetRoundEmpty(betround);
        isBetroundEnded(betround);
        List<UserBetEntity> userbet = userBetRepository.findAllByUserBetRoundId(userbetround.get().getId());
        userBetValidation(userbet);
        int dogruSayisi=0;
        for (int i = 0;i<gameMaxCount;i++){
             dogruSayisi+= oyunSonucuHesapla(betround.get().getGames().get(i),userbetround.get());
        }
        AllInOneEntity all = new AllInOneEntity();
        all.setBetRound(betround.get());
        all.setUserBetRound(userbetround.get());
        all.getUserBetRound().setCorrectGuessedMatchCount(dogruSayisi);

        all.setUser(user);
        all.setMesaj(all.getUserBetRound().getUserBetList().size()+" tane oyundan, "+all.getUserBetRound().getCorrectGuessedMatchCount() + " tanesini dogru bildiniz. Tebrikler "+ all.getUser().getFirstname() + "...");
        return all.getMesaj();

        }


































}
