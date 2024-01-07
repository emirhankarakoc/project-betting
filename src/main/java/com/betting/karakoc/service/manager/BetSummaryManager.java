package com.betting.karakoc.service.manager;

import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.enums.UserRole;
import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.service.repo.BetSummaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                UserBetEntityDTO dto = new UserBetEntityDTO();
                dto.setId(bet.getId());
                dto.setSelection(bet.getSelection());
                dto.setGameEntityId(bet.getGameEntityId());
                dto.setUserBetRoundId(bet.getUserBetRoundId());
                dto.setIsGuessCorrect(bet.getIsGuessCorrect());
                responseAllBets.add(dto);
            }
        }
        return responseAllBets;
    }

    public String summaryBets(Long userBetRoundId,String token){

        Optional<UserEntity> user = userRepository.findByToken(token);
        if (user.isEmpty()) throw new GeneralException("Invalid token.",400);
        Optional<UserBetRoundEntity> userbetround = userBetRoundRepository.findByIdAndUserEntityId(userBetRoundId,user.get().getId());
        if (userbetround.isEmpty()) throw new GeneralException("Invalid user bet round.",400);
        if (!(userbetround.get().getUserEntityId().equals(user.get().getId()))) throw new GeneralException("You didn't played this round.",400);
        Optional<BetRoundEntity> tokendenGelenUserinOynadigiUserBetRoundunBetRoundu = betRepository.findById(userbetround.get().getBetRoundEntityId());
        if(tokendenGelenUserinOynadigiUserBetRoundunBetRoundu.isEmpty()) throw new GeneralException("An error occured in summaryBets method.",400);
        if (tokendenGelenUserinOynadigiUserBetRoundunBetRoundu.get().getBetStatus()!=BetStatus.ENDED) throw new GeneralException("This betround is not finished yet.",400);
        //eger ended degilse kontrol etme bile.
        List<UserBetEntity> tokendenGelenKullanicininOynadigiUserBetRounddakiBetler = userBetRepository.findAllByUserBetRoundId(userbetround.get().getId());
        if (tokendenGelenKullanicininOynadigiUserBetRounddakiBetler.isEmpty()) throw new GeneralException("An error occured in summaryBets method.",400);
        if (tokendenGelenKullanicininOynadigiUserBetRounddakiBetler.size()!=13) throw new GeneralException("You have to bet all games.",400);
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
