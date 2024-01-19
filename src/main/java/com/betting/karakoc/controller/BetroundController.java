package com.betting.karakoc.controller;


import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.requests.CreateBetRoundRequest;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequestWithTwoTeams;
import com.betting.karakoc.security.annotations.IsAuthentificated;
import com.betting.karakoc.security.annotations.OnlyAdmin;
import com.betting.karakoc.service.abstracts.AdminService;
import com.betting.karakoc.service.abstracts.BetSummaryService;
import com.betting.karakoc.service.abstracts.MailSenderService;
import com.betting.karakoc.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/betrounds") //@TODO bunu birden fazla controllerlara böl (usercontroller, betroundcontroller)
@AllArgsConstructor
@Tag(name = "Betround Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
public class BetroundController {
    private final AdminService adminService;
    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final BetSummaryService betSummaryService;


    @Operation(summary = "creating a new betround")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @PostMapping
    @OnlyAdmin
    public BetRoundEntityDTO postBetRound(@RequestBody CreateBetRoundRequest request) {
        return adminService.createBetRound(request); // bir html işlemi
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
            return betSummaryService.hesapla(userBetRoundId);

        }*/
    @Operation(
            summary = "change the match score with gameId")
    @PutMapping("/{betroundId}/games/{gameId}")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public GameEntityDTO putGame(@PathVariable Long betroundId, @PathVariable Long gameId, @RequestBody PutGameRequestWithTwoTeams request) {
        return adminService.putGame(betroundId, gameId, request);
    }

    @Operation(
            summary = "brings betrounds which status is ENDED")
    @GetMapping("/filter/ended")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public List<BetRoundEntityDTO> getEndedBetRounds() {
        return userService.getEndedBetRounds();
    }

    @Operation(
            summary = "brings betrounds which status is PLANNED")

    @GetMapping("/filter/planned")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @IsAuthentificated
    public List<BetRoundEntityDTO> getPlannedBetRounds() {
        return userService.getPlannedBetRounds();
    }

    @Operation(
            summary = "brings all CREATED betrounds, after adding static game count(in main class), betrounds status automaticly changes to PLANNED\nso this method just gives \"NOT FINISHED YET betrounds\"")

    @GetMapping("/filter/created")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public List<BetRoundEntityDTO> getCreatedBetRounds() {
        return adminService.getCreatedBetRounds();
    }

    @Operation(
            summary = "creating an userbetround for given betroundId")

    @PostMapping("/{betRoundId}/userbetrounds")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserBetRoundEntityDTO createUserBetRound(@PathVariable Long betRoundId) {
        return userService.createUserBetRound(betRoundId);
    }



    @Operation(
            summary = "after all bets, finishes the betrounds manually.")
    @PutMapping("{betroundId}/end")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public BetRoundEntityDTO endBetRound(@PathVariable Long betroundId) {
        return adminService.endBetRound(betroundId);
    }

    @Operation(
            summary = "sends e-username to all users participating in this betround")
    @PutMapping("{betroundId}/finish")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public String mailSender(@PathVariable Long betroundId) {
        return mailSenderService.mailSenderByBetRoundId(betroundId);
    }
    @Operation(summary = "creating a game for given betround")
    @PostMapping("/{betroundId}/games")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public GameEntityDTO createGame(@PathVariable Long betroundId, @RequestBody CreateGameRequest request, @RequestParam int teamsSize) {
        return adminService.createGame(betroundId, request, teamsSize);
    }
    @Operation(summary = "brings all userbets for given userbetrounds id")
    @GetMapping("/{betroundId}/userbetrounds/{userbetRoundId}/bets")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    @OnlyAdmin
    public List<UserBetEntityDTO> getAllBetsByGame(@PathVariable Long userbetRoundId,@PathVariable Long betroundId) {
        return betSummaryService.getAllBetsByGame(userbetRoundId,betroundId);
    }
}
