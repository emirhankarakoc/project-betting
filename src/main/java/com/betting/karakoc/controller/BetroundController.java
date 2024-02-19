package com.betting.karakoc.controller;


import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.requests.*;
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
    @PutMapping("/putGame")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public GameEntityDTO putGame(@RequestBody PutGameRequest request) {
        return adminService.putGame(request);
    }

    @Operation(
            summary = "GET ALL ENDED BETROUNDS")
    @GetMapping("/getBetrounds/filter/ended")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getEndedBetRounds() {
        return userService.getEndedBetRounds();
    }

    @Operation(
            summary = "GET ALL PLANNED BETROUNDS")

    @GetMapping("/getBetrounds/filter/planned")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getPlannedBetRounds() {
        return userService.getPlannedBetRounds();
    }

    @Operation(
            summary = "GET ALL CREATED BETROUNDS")

    @GetMapping("/getBetrounds/filter/created")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getCreatedBetRounds(@RequestBody GetCreatedBetRoundsRequest request) {
        return adminService.getCreatedBetRounds(request);
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
    @PutMapping("/endBetRound")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public BetRoundEntityDTO endBetRound(@RequestBody EndBetRoundRequest request) {
        return adminService.endBetRound(request);
    }

    @Operation(
            summary = "FINISH BETROUND")
    @PutMapping("{betroundId}/finish")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public String mailSender(@PathVariable @NotNull @NotBlank @NotEmpty
                             Long betroundId) {
        return mailSenderService.mailSenderByBetRoundId(betroundId);
    }

    @Operation(summary = "CREATE GAME")
    @PostMapping("/{betroundId}/games")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public GameEntityDTO createGame(CreateGameRequest request) {
        return adminService.createGame(request);
    }

    @Operation(summary = "GET ALL USERBETS")
    @GetMapping("/{betroundId}/userbetrounds/{userbetRoundId}/bets")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
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
    @DeleteMapping("/deletebetRound")
    public void deleteBetRound(@RequestBody DeleteBetRoundRequest request){
        adminService.deleteBetRound(request);
    }
    @Operation(summary = "DELETE GAME")

    @DeleteMapping("/deleteGame")
    public void deleteGame(@RequestBody DeleteGameRequest request){
        adminService.deleteGame(request);
    }
    @Operation(summary = "DELETE USERBETROUND")

    @DeleteMapping("/deleteUserBetRound")
    public void deleteUserBetRound(@RequestBody DeleteUserBetRoundRequest request){
        adminService.deleteUserBetRound(request);
    }
    @Operation(summary = "DELETE BET")

    @DeleteMapping("/deleteBet")
    public void deleteBet(@RequestBody DeleteBetRequest request){
        adminService.deleteBet(request));
    }




}
