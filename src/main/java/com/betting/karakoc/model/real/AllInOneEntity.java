package com.betting.karakoc.model.real;


import lombok.Data;

import java.util.List;

@Data
public class AllInOneEntity {
    BetRoundEntity betRound;
    UserBetRoundEntity userBetRound;
    UserEntity user;
    String mesaj;
}
