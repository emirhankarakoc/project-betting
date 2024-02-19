package com.betting.karakoc.controller;


import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.GameEntityDTO;
import com.betting.karakoc.models.dtos.UserBetEntityDTO;
import com.betting.karakoc.models.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.models.requests.*;
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
    @PostMapping("/createBetRound")
    public BetRoundEntityDTO postBetRound(@RequestBody
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
    public List<BetRoundEntityDTO> getEndedBetRounds(@RequestBody GetEndedBetRoundsRequest request) {
        return userService.getEndedBetRounds( request);
    }

    @Operation(
            summary = "GET ALL PLANNED BETROUNDS")

    @GetMapping("/getBetrounds/filter/planned")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getPlannedBetRounds(@RequestBody GetPlannedBetRoundsRequest request) {
        return userService.getPlannedBetRounds(request);
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

    @PostMapping("/createUserBetRound")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public UserBetRoundEntityDTO createUserBetRound(@RequestBody  CreateUserBetRoundRequest request) {
        return userService.createUserBetRound(request);
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
    @PutMapping("/finish")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public String mailSender(@RequestBody MailSenderByBetRoundIdRequest request) {
        return mailSenderService.mailSenderByBetRoundId(request);
    }

    @Operation(summary = "CREATE GAME")
    @PostMapping("/createGame")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public GameEntityDTO createGame(@RequestBody CreateGameRequest request) throws InterruptedException {
        return adminService.createGame(request);
    }

    @Operation(summary = "GET ALL USERBETS")
    @GetMapping("/getAllBetsByGame")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<UserBetEntityDTO> getAllBetsByGame(@RequestBody GetAllBetsByGameRequest request) {
        return betSummaryService.getAllBetsByGame(request);
    }


    @PostMapping("/createUserBet")
    public UserBetEntityDTO createUserBet(@RequestBody CreateBetRequest request) {
        return userService.creteUserBet(request);
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
        adminService.deleteBet(request);
    }




}
