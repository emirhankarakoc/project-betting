package com.betting.karakoc.model.requests;


import lombok.Data;

@Data
public class CreateGameRequest {
    private String firstTeam;
    private String secondTeam;
}
