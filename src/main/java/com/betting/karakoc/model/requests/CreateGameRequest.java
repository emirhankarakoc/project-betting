package com.betting.karakoc.model.requests;


import com.betting.karakoc.model.enums.GameType;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
  private List<String> teams;
  private GameType gameType;


}
