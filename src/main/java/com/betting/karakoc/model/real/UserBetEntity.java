package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.KarakocApplication.gameMaxCount;

@Entity
@Data
public class UserBetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long userBetRoundId;
    private Long gameEntityId;
    private Long betTeamId;
    private Boolean isGuessCorrect;


    public static void userBetValidation(List<UserBetEntity> list) {
        if (list.isEmpty()) throw new BadRequestException("An error occured in summaryBets method.");
        if (list.size() != gameMaxCount) throw new BadRequestException("You have to bet all games.");
    }

    public static UserBetEntityDTO userBetToDto(UserBetEntity userBet) {

        UserBetEntityDTO dto = new UserBetEntityDTO();
        dto.setId(userBet.getId());
        dto.setBetTeamId(userBet.getBetTeamId());
        dto.setGameEntityId(userBet.getGameEntityId());
        dto.setUserBetRoundId(userBet.getUserBetRoundId());
        dto.setIsGuessCorrect(userBet.getIsGuessCorrect());
        return dto;
    }

    public static void isUserBetIsPresent(Optional<UserBetEntity> userBet) {
        if (userBet.isPresent()) throw new BadRequestException("You already put your bet.");

    }


}
