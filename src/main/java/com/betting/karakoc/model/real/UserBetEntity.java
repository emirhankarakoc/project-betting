package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.enums.Selection;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Entity
@Data
public class UserBetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long userBetRoundId;
    private Long gameEntityId;
    @Enumerated
    private Selection selection; // 1  0  2
    private Boolean isGuessCorrect;







    public static void userBetValidation(List<UserBetEntity> list){
        if (list.isEmpty())throw new GeneralException("An error occured in summaryBets method.",400);
        if (list.size()!=13) throw new GeneralException("You have to bet all games.",400);
    }

    public static UserBetEntityDTO userBetToDto(UserBetEntity userBet){

        UserBetEntityDTO dto = new UserBetEntityDTO();
        dto.setId(userBet.getId());
        dto.setSelection(userBet.getSelection());
        dto.setGameEntityId(userBet.getGameEntityId());
        dto.setUserBetRoundId(userBet.getUserBetRoundId());
        dto.setIsGuessCorrect(userBet.getIsGuessCorrect());
        return dto;
    }
    public static void isUserBetIsPresent(Optional<UserBetEntity> userBet){
        if (userBet.isPresent()) throw new GeneralException("You already put your bet.",400);

    }



}
