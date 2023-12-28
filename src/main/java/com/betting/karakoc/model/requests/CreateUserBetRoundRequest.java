package com.betting.karakoc.model.requests;

import com.betting.karakoc.model.real.UserBetEntity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data

public class CreateUserBetRoundRequest {

    private Long betRoundEntityId;

}
