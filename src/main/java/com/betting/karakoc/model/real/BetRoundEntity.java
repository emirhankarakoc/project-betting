package com.betting.karakoc.model.real;

import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.enums.BetStatus;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.KarakocApplication.gameMaxCount;

@Entity
@Data
public class BetRoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String title;
    private LocalDateTime playDateTime;
    @Enumerated
    private BetStatus betStatus;
    @OneToMany
    @JoinColumn(name = "betroundId")
    private List<GameEntity> games;



public static void isBetRoundEmpty(Optional<BetRoundEntity> betround ){
    if(betround.isEmpty()) throw new GeneralException("There is not a betround with this id, check one more time..",400);
}
public static void isBetroundEnded(Optional<BetRoundEntity> betround){
    if (betround.get().getBetStatus()!=BetStatus.ENDED) throw new GeneralException("This betround is not finished yet.",400);

}
public static void isBetroundsGameSizeLowerThanXX(BetRoundEntity betRound){
    if (betRound.getGames().size()==gameMaxCount) throw new GeneralException("Cant add, game count must be 3.",400);

}
public static void setPlannedIfGamesSizeIsXX(BetRoundEntity betRound){
    if (betRound.getGames().size()==gameMaxCount){
        betRound.setBetStatus(BetStatus.PLANNED);
    }
}

public static void isPlayDatePast(LocalDateTime date){
    if (date.isBefore(LocalDateTime.now())) throw new GeneralException("You cant add a betround which have past playdate.",400);
}
public static void isBetRoundsGameIsNotXX(BetRoundEntity betRound){
    if (betRound.getGames().size()!=gameMaxCount) throw new GeneralException("You cant create userbetround for this betround, betrounds games isnt enough now: "+betRound.getGames().size()+"...   Must be 3",400);

}
public static void isBetroundStatusCreatedOrEnded(BetRoundEntity betRound){
    if (betRound.getBetStatus()==BetStatus.ENDED || betRound.getBetStatus()==BetStatus.CREATED) throw new GeneralException("You cant access this betround at this moment.",400);

}
public static BetRoundEntity createBetRoundBuilder(CreateBetRoundRequest request){
    BetRoundEntity betRound = new BetRoundEntity();
    betRound.setTitle(request.getTitle());
    betRound.setBetStatus(BetStatus.CREATED);
    betRound.setCreatedDateTime(LocalDate.now());
    betRound.setUpdatedDateTime(LocalDate.now());
    betRound.setPlayDateTime(request.getPlayDateTime());
    betRound.setGames(new ArrayList<>());
    return betRound;
}
public static BetRoundEntityDTO betroundToDto(BetRoundEntity betRound){
    BetRoundEntityDTO dto = new BetRoundEntityDTO();
    dto.setId(betRound.getId());
    dto.setTitle(betRound.getTitle());
    dto.setStatus(betRound.getBetStatus());
    dto.setCreatedDateTime(betRound.getCreatedDateTime());
    dto.setUpdatedDateTime(betRound.getUpdatedDateTime());
    dto.setPlayDateTime(betRound.getPlayDateTime());
    dto.setGames(betRound.getGames());
    for (int i = 0;i<betRound.getGames().size();i++){
        for (int j = 0;j<betRound.getGames().get(i).getTeams().size();j++){
            dto.getGames().get(i).setTeams(betRound.getGames().get(i).getTeams());

    }
    }

    return dto;
}

}
