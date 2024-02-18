package com.betting.karakoc.models.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.enums.BetStatus;
import com.betting.karakoc.models.requests.CreateBetRoundRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betting.karakoc.KarakocApplication.GAME_MAX_COUNT;

@Entity
@Data
public class BetRoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;
    @NotNull@NotBlank@NotEmpty
    private LocalDate createdDateTime;
    @NotNull@NotBlank@NotEmpty
    private LocalDate updatedDateTime;
    @NotNull@NotBlank@NotEmpty
    private String title;
    @NotNull@NotBlank@NotEmpty
    private LocalDateTime playDateTime;
    @Enumerated
    @NotNull@NotBlank@NotEmpty
    private BetStatus betStatus;
    @OneToMany
    @JoinColumn(name = "betroundId")
    @NotNull@NotBlank@NotEmpty
    private List<GameEntity> games;


    public static void isBetRoundEmpty(Optional<BetRoundEntity> betround) {
        if (betround.isEmpty())
            throw new NotfoundException("There is not a betround with this id, check one more time..");
    }

    public static void isBetroundEnded(Optional<BetRoundEntity> betround) {
        if (betround.get().getBetStatus() != BetStatus.ENDED)
            throw new BadRequestException("This betround is not finished yet.");

    }

    public static void isBetroundsGameSizeLowerThanXX(BetRoundEntity betRound) {
        if (betRound.getGames().size() == GAME_MAX_COUNT)
            throw new BadRequestException("Cant add, game count must be " + GAME_MAX_COUNT + ".");

    }

    public static void setPlannedIfGamesSizeIsXX(BetRoundEntity betRound) {
        if (betRound.getGames().size() == GAME_MAX_COUNT) {
            betRound.setBetStatus(BetStatus.PLANNED);
        }
    }

    public static void isPlayDatePast(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now()))
            throw new BadRequestException("You cant add a betround which have past playdate.");
    }

    public static void isBetRoundsGameIsNotXX(BetRoundEntity betRound) {
        if (betRound.getGames().size() != GAME_MAX_COUNT)
            throw new BadRequestException("You cant create userbetround for this betround, betrounds games isnt enough now... Games count: " + betRound.getGames().size() + "...   Must be :" + GAME_MAX_COUNT);

    }

    public static void isBetroundStatusCreatedOrEnded(BetRoundEntity betRound) {
        if (betRound.getBetStatus() == BetStatus.ENDED || betRound.getBetStatus() == BetStatus.CREATED)
            throw new BadRequestException("You cant access this betround at this moment.");

    }

    public static BetRoundEntity createBetRoundBuilder(CreateBetRoundRequest request) {
        BetRoundEntity betRound = new BetRoundEntity();
        betRound.setTitle(request.getTitle());
        betRound.setBetStatus(BetStatus.CREATED);
        betRound.setCreatedDateTime(LocalDate.now());
        betRound.setUpdatedDateTime(LocalDate.now());
        betRound.setPlayDateTime(request.getLastBetTime());
        betRound.setGames(new ArrayList<>());
        return betRound;
    }

    public static BetRoundEntityDTO betroundToDto(BetRoundEntity betRound) {
        BetRoundEntityDTO dto = new BetRoundEntityDTO();
        dto.setId(betRound.getId());
        dto.setTitle(betRound.getTitle());
        dto.setStatus(betRound.getBetStatus());
        dto.setCreatedDateTime(betRound.getCreatedDateTime());
        dto.setUpdatedDateTime(betRound.getUpdatedDateTime());
        dto.setPlayDateTime(betRound.getPlayDateTime());
        dto.setGames(betRound.getGames());
        for (int i = 0; i < betRound.getGames().size(); i++) {
            for (int j = 0; j < betRound.getGames().get(i).getTeams().size(); j++) {
                dto.getGames().get(i).setTeams(betRound.getGames().get(i).getTeams());

            }
        }

        return dto;
    }

}
