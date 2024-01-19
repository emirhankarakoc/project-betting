package com.betting.karakoc.model.requests;


import com.betting.karakoc.model.enums.Selection;
import lombok.Data;

@Data
public class ResponseAllBets {
    String firstname;
    String username;
    String betRoundTitle;
    String firstTeam;
    String scoreFirstTeam;
    String secondTeam;
    String scoreSecondTeam;
    Selection selection;

    //a


}
