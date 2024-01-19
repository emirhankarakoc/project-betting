package com.betting.karakoc.controller;


import com.betting.karakoc.model.dtos.UserBetEntityDTO;
import com.betting.karakoc.model.dtos.UserBetRoundEntityDTO;
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
@RequestMapping("/userbetrounds")
@AllArgsConstructor
@Tag(name = "Userbetround Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
public class UserBetRoundController {
    private final AdminService adminService;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final BetSummaryService betSummaryService;


}
