package com.betting.karakoc.controller;


import com.betting.karakoc.model.dtos.BetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.Selection;
import com.betting.karakoc.model.real.BetRoundEntity;


import com.betting.karakoc.service.repo.MailService;
import com.betting.karakoc.service.repo.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bet/user")
@AllArgsConstructor
@Tag(name = "User Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")


public class UserController {
    private final UserService service;
    private final MailService mailService;


    @Operation(
            summary = "brings betrounds which status is ENDED")
    @GetMapping("/endedBetRounds")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public List<BetRoundEntityDTO> getEndedBetRounds() {
        return service.getEndedBetRounds();
    }
    @Operation(
            summary = "brings betrounds which status is PLANNED")

    @GetMapping("/plannedBetRounds")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public List<BetRoundEntityDTO> getPlannedBetRounds() {
        return service.getPlannedBetRounds();
    }
    @Operation(
            summary = "creating an userbetround for given betroundId")

    @PostMapping("/createUserBetRound")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserBetRoundEntityDTO createUserBetRound(@RequestParam Long betRoundId){
        return service.createUserBetRound(betRoundId);
    }

    @Operation(
            summary = "create a bet for given gameId and given userBetRoundId")
    @PostMapping("/createBet")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserBetEntityDTO creteUserBet(@RequestParam Long userBetRoundId, @RequestParam Long gameId,@RequestParam Long betTeamId){
        return service.creteUserBet(userBetRoundId, gameId, betTeamId);
    }/*
    @Operation(
            summary = "brings a specific betround, include all games.")
    @GetMapping("/getBetRound")
    public BetRoundEntityDTO getBetroundById(@RequestParam Long id){
        return service.getBetroundById(id);

        //bu metod aslinda userlarin kullanimina acik degil. ben emirhan test ederken bunu ekledim. geliyo mu diye, silmedim dursun belki ilerde lazim olur.
        //kullanicilarin CREATED,PLANNED ve ENDED betroundlara ayri ayri erisimi var. getbetround da olsa , olmaz.
        // o yuzden yoruma alindi.
    }*/

    @Operation(
            summary = "changing password")
    @PostMapping("/changePassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserEntityDTO changePassword(String username, String password, String newPassword){return service.changePassword(username,password,newPassword);}


    @Operation(
            summary = "sends a new password to users mail adress")
    @GetMapping("/forgotPassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public String forgotPassword(@RequestParam String username){return mailService.forgotPassword(username);}

}
