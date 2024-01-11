package com.betting.karakoc.controller;

import com.betting.karakoc.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bet/db")
@AllArgsConstructor
@Tag(name = "To clean the database")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

public class DBClearController {
    private final UserEntityRepository repo0;
    private final BetRoundEntityRepository repo1;
    private final GameRepository repo2;
    private final UserBetRoundRepository repo3;
    private final UserBetRepository repo4;
    @Operation(summary = "for fresh start")
    @DeleteMapping
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public void deleteAllDB(){
        repo0.deleteAll();
        repo1.deleteAll();
        repo2.deleteAll();
        repo3.deleteAll();
        repo4.deleteAll();
    }

}
