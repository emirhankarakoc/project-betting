package com.betting.karakoc.model.requests;

import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.real.GameEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateBetRoundRequest {

    private String title;
    private LocalDateTime playDateTime;

}
