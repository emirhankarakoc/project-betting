package com.betting.karakoc.models.real;

import com.betting.karakoc.exceptions.general.BadRequestException;
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

@Entity
@Data
public class UserBetRoundEntity {
    //kupon
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;
    @NotNull@NotBlank@NotEmpty
    private LocalDate createdDateTime;
    @NotNull@NotBlank@NotEmpty
    private LocalDate updatedDateTime;
    @NotNull@NotBlank@NotEmpty
    private String userEntityId;
    @NotNull@NotBlank@NotEmpty
    private Long betRoundEntityId;
    @OneToMany
    @JoinColumn(name = "userBetRoundId")
    @NotNull@NotBlank@NotEmpty
    private List<UserBetEntity> userBetList;
    @NotNull@NotBlank@NotEmpty
    private int correctGuessedMatchCount;


    public static void isUserBetRoundEmptyAndisUserPlayedForThisBetround(Optional<UserBetRoundEntity> userBetRoundEntity, UserEntity user) {
        if (userBetRoundEntity.isEmpty()) throw new BadRequestException("Invalid user bet round.");
        if (!(userBetRoundEntity.get().getUserEntityId().equals(user.getId())))
            throw new BadRequestException("You didn't played this round.");

    }

    public static UserBetRoundEntity createUserbetRound(Long betRoundEntityId, UserEntity user) {
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

    public static void isUserBetRoundEmpty(Optional<UserBetRoundEntity> userBetRound) {
        if (userBetRound.isEmpty()) throw new NotfoundException("Invalid User Bet Round Id.");
    }
}
