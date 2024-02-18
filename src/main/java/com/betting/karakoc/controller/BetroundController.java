package com.betting.karakoc.controller;


import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.requests.CreateBetRoundRequest;
import com.betting.karakoc.models.requests.CreateGameRequest;
import com.betting.karakoc.models.requests.PutGameRequest;
import com.betting.karakoc.security.annotations.IsAuthentificated;
import com.betting.karakoc.security.annotations.OnlyAdmin;
import com.betting.karakoc.service.interfaces.AdminService;
import com.betting.karakoc.service.interfaces.BetSummaryService;
import com.betting.karakoc.service.interfaces.MailSenderService;
import com.betting.karakoc.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/betrounds")
@AllArgsConstructor
@Tag(name = "Betround Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
public class BetroundController {
    private final AdminService adminService;
    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final BetSummaryService betSummaryService;


    @Operation(summary = "CREATE BETROUND")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @PostMapping
    @OnlyAdmin
    public BetRoundEntityDTO postBetRound(@RequestBody @NotNull @NotBlank @NotEmpty
                                          CreateBetRoundRequest request) {
        return adminService.createBetRound(request);
    }


    /*@PostMapping("/changeToTurtle/{gameId}")
    @OnlyAdmin
    public GameEntity changeGameModuleToTurtleGame(@PathVariable Long gameId) {
        return service.changeGameModuleToTurtleGame(gameId);
    }*/


    /*
        @Operation(summary = "brings a text like\nCONGRATS x, YOU WIN y GAME")
        @PostMapping("/{userBetRoundId}/summary")
        @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
        @OnlyAdmin
        public String summaryBets(@PathVariable Long userBetRoundId) {
            return betSummaryService.summary(userBetRoundId);

        }*/
    @Operation(
            summary = "PUT GAME")
    @PutMapping("/{betroundId}/games/{gameId}")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public GameEntityDTO putGame(@PathVariable @NotNull @NotBlank @NotEmpty
                                 Long betroundId, @PathVariable @NotNull @NotBlank @NotEmpty
                                 Long gameId, @RequestBody @NotNull @NotBlank @NotEmpty
                                 PutGameRequest request) {
        return adminService.putGame(betroundId, gameId, request);
    }

    @Operation(
            summary = "GET ALL ENDED BETROUNDS")
    @GetMapping("/filter/ended")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getEndedBetRounds() {
        return userService.getEndedBetRounds();
    }

    @Operation(
            summary = "GET ALL PLANNED BETROUNDS")

    @GetMapping("/filter/planned")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @IsAuthentificated
    public List<BetRoundEntityDTO> getPlannedBetRounds() {
        return userService.getPlannedBetRounds();
    }

    @Operation(
            summary = "GET ALL CREATED BETROUNDS")

    @GetMapping("/filter/created")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public List<BetRoundEntityDTO> getCreatedBetRounds() {
        return adminService.getCreatedBetRounds();
    }

    @Operation(
            summary = "CREATE USERBETROUND")

    @PostMapping("/{betRoundId}/userbetrounds")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserBetRoundEntityDTO createUserBetRound(@PathVariable @NotNull @NotBlank @NotEmpty
                                                    Long betRoundId) {
        return userService.createUserBetRound(betRoundId);
    }


    @Operation(
            summary = "END BETROUND")
    @PutMapping("{betroundId}/end")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public BetRoundEntityDTO endBetRound(@PathVariable @NotNull @NotBlank @NotEmpty
                                         Long betroundId) {
        return adminService.endBetRound(betroundId);
    }

    @Operation(
            summary = "FINISH BETROUND")
    @PutMapping("{betroundId}/finish")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public String mailSender(@PathVariable @NotNull @NotBlank @NotEmpty
                             Long betroundId) {
        return mailSenderService.mailSenderByBetRoundId(betroundId);
    }

    @Operation(summary = "CREATE GAME")
    @PostMapping("/{betroundId}/games")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public GameEntityDTO createGame(@PathVariable @NotNull @NotBlank @NotEmpty
                                    Long betroundId, @RequestBody @NotNull @NotBlank @NotEmpty
                                    CreateGameRequest request, @NotNull @NotBlank @NotEmpty
                                    @RequestParam int teamsSize) {
        return adminService.createGame(betroundId, request, teamsSize);
    }

    @Operation(summary = "GET ALL USERBETS")
    @GetMapping("/{betroundId}/userbetrounds/{userbetRoundId}/bets")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public List<UserBetEntityDTO> getAllBetsByGame(@PathVariable @NotNull @NotBlank @NotEmpty
                                                   Long userbetRoundId, @PathVariable @NotNull @NotBlank @NotEmpty
                                                   Long betroundId) {
        return betSummaryService.getAllBetsByGame(userbetRoundId, betroundId);
    }


    @PostMapping("{betroundId}/userbetrounds/{userbetroundId}/games/{gameId}/bet")
    public UserBetEntityDTO makeBet(@PathVariable @NotNull @NotBlank @NotEmpty
                                    Long betroundId, @PathVariable @NotNull @NotBlank @NotEmpty
                                    Long userbetroundId, @PathVariable @NotNull @NotBlank @NotEmpty
                                    Long gameId, @NotNull @NotBlank @NotEmpty
                                    Long betTeamId) {
        return userService.creteUserBet(betroundId, userbetroundId, gameId, betTeamId);
    }

@Operation(summary = "DELETE BETROUND")
    @DeleteMapping("{betroundId}")
    public void deleteBetround(@PathVariable @NotNull @NotBlank @NotEmpty Long betroundId){
        adminService.deleteBetRound(betroundId);
    }
    @Operation(summary = "DELETE GAME")

    @DeleteMapping("{betroundId}/games/{gameId}")
    public void deleteGame(@PathVariable@NotNull @NotBlank @NotEmpty Long betroundId,@PathVariable @NotNull @NotBlank @NotEmpty Long gameId){
        adminService.deleteGame(betroundId,gameId);
    }
    @Operation(summary = "DELETE USERBETROUND")

    @DeleteMapping("{betroundId}/userbetrounds/{userbetroundId}")
    public void deleteUserBetround(@PathVariable @NotNull @NotBlank @NotEmpty Long betroundId,@PathVariable @NotNull @NotBlank @NotEmpty Long userbetroundId){
        adminService.deleteUserBetRound(betroundId,userbetroundId);
    }
    @Operation(summary = "DELETE BET")

    @DeleteMapping("{betroundId}/userbetrounds/{userbetroundId}/bets/{betId}")
    public void deleteBet(@PathVariable @NotNull @NotBlank @NotEmpty Long betroundId,@PathVariable @NotNull @NotBlank @NotEmpty Long userbetroundId, @PathVariable @NotNull @NotBlank @NotEmpty String betId){
        adminService.deleteBet(betroundId,userbetroundId,betId);
    }




}
