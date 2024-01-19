package com.betting.karakoc.controller;

import com.betting.karakoc.model.dtos.GameEntityDTO;
import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.requests.CreateGameRequest;
import com.betting.karakoc.model.requests.PutGameRequestWithTwoTeams;
import com.betting.karakoc.security.annotations.OnlyAdmin;
import com.betting.karakoc.service.abstracts.AdminService;
import com.betting.karakoc.service.abstracts.BetSummaryService;
import com.betting.karakoc.service.abstracts.MailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Tag(name = "Game Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
public class GameController {

    private final BetSummaryService betSummaryService;
    private final AdminService adminService;
    private final MailSenderService mailSenderService;















}
