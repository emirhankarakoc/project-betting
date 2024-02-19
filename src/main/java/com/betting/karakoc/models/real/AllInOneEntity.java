package com.betting.karakoc.models.real;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AllInOneEntity {
    BetRoundEntity betRound;
    UserBetRoundEntity userBetRound;
    String message;
}
