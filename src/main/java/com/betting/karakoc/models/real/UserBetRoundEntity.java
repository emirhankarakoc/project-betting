package com.betting.karakoc.models.real;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.ForbiddenException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Data
public class UserBetRoundEntity {
    //kupon
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;

    private LocalDate createdDateTime;

    private LocalDate updatedDateTime;
    private String userToken;
    private String betRoundEntityId;
    @OneToMany
    @JoinColumn(name = "userBetRoundId")
    private List<UserBetEntity> userBetList;
    private int correctGuessedMatchCount;


    public static void isUserBetRoundEmptyAndisUserPlayedForThisBetround(Optional<UserBetRoundEntity> userBetRoundEntity, UserEntity user) {

        if (!(userBetRoundEntity.get().getUserToken().equals(user.getToken())))
            throw new BadRequestException("You didn't played this round.");

    }


    public static UserBetRoundEntity createUserbetRound(String betRoundEntityId, UserEntity user) {
        UserBetRoundEntity userBetRound = new UserBetRoundEntity();
        userBetRound.setId(UUID.randomUUID().toString());
        userBetRound.setCreatedDateTime(LocalDate.now());
        userBetRound.setUpdatedDateTime(LocalDate.now());
        userBetRound.setUserToken(user.getToken());
        userBetRound.setBetRoundEntityId(betRoundEntityId);
        userBetRound.setUserBetList(null);
        userBetRound.setCorrectGuessedMatchCount(0);
        return userBetRound;
    }

    public static UserBetRoundEntityDTO userBetRoundToDto(UserBetRoundEntity userBetRound) {
        UserBetRoundEntityDTO dto = new UserBetRoundEntityDTO();
        dto.setId(userBetRound.getId());
        dto.setCreatedDateTime(userBetRound.getCreatedDateTime());
        dto.setUpdatedDateTime(userBetRound.getUpdatedDateTime());
        dto.setUserToken(userBetRound.getUserToken());
        dto.setBetRoundEntityId(userBetRound.getBetRoundEntityId());
        dto.setUserBetList(userBetRound.getUserBetList());
        dto.setCorrectGuessedMatchCount(userBetRound.getCorrectGuessedMatchCount());
        return dto;
    }

    public static void isUserBetRoundEmpty(Optional<UserBetRoundEntity> userBetRound) {
        if (userBetRound.isEmpty()) throw new BadRequestException("Invalid User Bet Round Id.");
    }
}
