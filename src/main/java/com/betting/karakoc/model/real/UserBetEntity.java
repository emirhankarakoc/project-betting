package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.KarakocApplication.GAME_MAX_COUNT;

@Entity
@Data
public class UserBetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @NotBlank
    @NotEmpty
    private String id;
    @NotNull@NotBlank@NotEmpty
    private Long userBetRoundId;
    @NotNull@NotBlank@NotEmpty
    private Long gameEntityId;
    @NotNull@NotBlank@NotEmpty
    private Long betTeamId;
    @NotNull@NotBlank@NotEmpty
    private Boolean isGuessCorrect;


    public static void userBetValidation(List<UserBetEntity> list) {
        if (list.isEmpty()) throw new BadRequestException("An error occured in summaryBets method.");
        if (list.size() != GAME_MAX_COUNT) throw new BadRequestException("You have to bet all games.");
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

    public static void isUserBetEmpty(Optional<UserBetEntity> userBet) {
        if (userBet.isEmpty()) throw new NotfoundException("Invalid bet id");

    }


}
