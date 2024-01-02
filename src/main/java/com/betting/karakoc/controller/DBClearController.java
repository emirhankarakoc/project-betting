package com.betting.karakoc.controller;

import com.betting.karakoc.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bet/db")
@AllArgsConstructor
@Tag(name = "To clean the database")
public class DBClearController {
    private final UserEntityRepository repo0;
    private final BetRoundEntityRepository repo1;
    private final GameRepository repo2;
    private final UserBetRoundRepository repo3;
    private final UserBetRepository repo4;
    @Operation(summary = "for fresh start")
    @DeleteMapping
    public void deleteAllDB(){
        repo0.deleteAll();
        repo1.deleteAll();
        repo2.deleteAll();
        repo3.deleteAll();
        repo4.deleteAll();
    }

}
