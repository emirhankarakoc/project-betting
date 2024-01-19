package com.betting.karakoc.model.real;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class UserBetRoundEntity {
    //kupon
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String userEntityId;
    private Long betRoundEntityId;
    @OneToMany
    @JoinColumn(name = "userBetRoundId")
    private List<UserBetEntity> userBetList;
    private int correctGuessedMatchCount;


    public static void isUserBetRoundEmptyAndisUserPlayedForThisBetround(Optional<UserBetRoundEntity> userBetRoundEntity, UserEntity user) {
        if (userBetRoundEntity.isEmpty()) throw new BadRequestException("Invalid user bet round.");
        if (!(userBetRoundEntity.get().getUserEntityId().equals(user.getId())))
            throw new BadRequestException("You didn't played this round.");

    }

    public static UserBetRoundEntity createUserbetRoundBuilder(Long betRoundEntityId, UserEntity user) {
        UserBetRoundEntity userBetRound = new UserBetRoundEntity();
        userBetRound.setCreatedDateTime(LocalDate.now());
        userBetRound.setUpdatedDateTime(LocalDate.now());
        userBetRound.setUserEntityId(user.getId());
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
        dto.setUserEntityId(userBetRound.getUserEntityId());
        dto.setBetRoundEntityId(userBetRound.getBetRoundEntityId());
        dto.setUserBetList(userBetRound.getUserBetList());
        dto.setCorrectGuessedMatchCount(userBetRound.getCorrectGuessedMatchCount());
        return dto;

    }

    public static void isUserBetRoundIsEmpty(Optional<UserBetRoundEntity> userBetRound) {
        if (userBetRound.isEmpty()) throw new NotfoundException("Invalid Bet Round Id.");
    }
}
