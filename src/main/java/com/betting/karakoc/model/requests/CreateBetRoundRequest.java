package com.betting.karakoc.model.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBetRoundRequest {

    private String title;
    private LocalDateTime playDateTime;

}
