package com.betting.karakoc.model.requests;


import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
  private List<String> teams;


}
