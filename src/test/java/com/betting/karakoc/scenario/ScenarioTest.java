package com.betting.karakoc.scenario;

import com.betting.karakoc.models.enums.GameType;
import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.Team;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.*;
import com.betting.karakoc.repository.UserEntityRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseLogSpec;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.betting.karakoc.KarakocApplicationTests.API_URL;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@Data
class ScenarioTest {


    @Autowired
    private UserEntityRepository userRepository;

    @Test

    void allInTest(){
        String betroundId = "";
        String gameId1="";//will init
        String gameId2="";//will init
        String adminToken = "1";//dont touch
        String userToken = "";//will init
        String userBetRoundId = "";//will init

        List<String> teams = new ArrayList<>(); teams.add("fenerbahce");teams.add("besiktas");
        List<Integer> scoresForGame1 = new ArrayList<>(); scoresForGame1.add(16);scoresForGame1.add(15); // we will bet 1-3. so this match is win.
        List<Integer> scoresForGame2 = new ArrayList<>(); scoresForGame2.add(5);scoresForGame2.add(4); // we will bet 1-3. so this match is lose.
        System.out.println("Initializer/ Done.");
//register
        CreateUserRequest poor_guy =  CreateUserRequest.builder().firstname("EMIRHAN").lastname("KARAKOC").username("jessuothebusiness@gmail.com").password("12345678").repeatPassword("12345678").build();
        ValidatableResponse user = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_guy).post("/account/register").then();
        user.statusCode(200);
        userToken = user.extract().path("token");
        System.out.println("Register/ Done");
// create a betround
        CreateBetRoundRequest poor_betround = CreateBetRoundRequest.builder().title("LEGENDARY MATCHES").lastBetTime(LocalDate.now().plusDays(1)).adminToken(adminToken).build();
        ValidatableResponse betround = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_betround).post("/betrounds/createBetRound").then();
        betround.statusCode(200);
        betroundId = betround.extract().path("id");
        System.out.println("Creating betround/ Done");
// create a game

        CreateGameRequest poor_game = CreateGameRequest.builder().betroundId(betroundId).gameType(GameType.TIME).adminToken(adminToken).teams(teams).teamsSize(2).build();
        ValidatableResponse game = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_game).post("/betrounds/createGame").then();
        game.statusCode(200);
        gameId1 = game.extract().path("id");
        System.out.println("Creating game/ Done");
        //team id's --> first one 1, second is 2;

//create one more game
// we need it because our GAME_MAX_COUNT = 2 in KarakocApplication.java
        CreateGameRequest poor_game2 = CreateGameRequest.builder().betroundId(betroundId).gameType(GameType.TIME).adminToken(adminToken).teams(teams).teamsSize(2).build();
        ValidatableResponse game2 = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_game2).post("/betrounds/createGame").then();
        game2.statusCode(200);
        gameId2 = game2.extract().path("id");
        System.out.println("Creating 2nd game/ Done");
        //team id's --> first one 3, second is 4;

//creating userbetround
        CreateUserBetRoundRequest poor_userbetround = CreateUserBetRoundRequest.builder().betRoundEntityId(betroundId).userToken(userToken).build();
        ValidatableResponse userbetround = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_userbetround).post("/betrounds/createUserBetRound").then();
        userbetround.statusCode(200);
        userBetRoundId = userbetround.extract().path("id");
        System.out.println("userbetround created with this id" + userBetRoundId);

//creating bets

        CreateBetRequest poor_bet = CreateBetRequest.builder().betRoundId(betroundId).gameId(gameId1).betTeamId(1).userBetRoundId(userBetRoundId).userToken(userToken).build();
        ValidatableResponse bet1 = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_bet).post("/betrounds/createUserBet").then();

        bet1.statusCode(200);
        System.out.println("creating bet/Done");

        CreateBetRequest poor_bet2 = CreateBetRequest.builder().betRoundId(betroundId).gameId(gameId2).betTeamId(3).userBetRoundId(userBetRoundId).userToken(userToken).build();
        ValidatableResponse bet2 = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(poor_bet2).post("/betrounds/createUserBet").then();
        bet2.statusCode(200);
        System.out.println("creating 2nd bet/Done");

//end bets
        EndBetRoundRequest endBetRoundRequest = EndBetRoundRequest.builder().betroundId(betroundId).adminToken(adminToken).build();
        ValidatableResponse endBetRoundReq = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(endBetRoundRequest).put("/betrounds/endBetRound").then();
        endBetRoundReq.statusCode(200);
        System.out.println("end bets/ Done");

//applying scores to the matches
        PutGameRequest putGameRequest1 = PutGameRequest.builder().betRoundId(betroundId).adminToken(adminToken).gameId(gameId1).scores(scoresForGame1).build();
        ValidatableResponse putGameReq1 = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(putGameRequest1).put("/betrounds/putGame").then();
        putGameReq1.statusCode(200);

        System.out.println("changing the 1st match score/Done");


        PutGameRequest putGameRequest2 = PutGameRequest.builder().betRoundId(betroundId).adminToken(adminToken).gameId(gameId1).scores(scoresForGame2).build();
        ValidatableResponse putGameReq2 = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(putGameRequest2).put("/betrounds/putGame").then();
        putGameReq2.statusCode(200);
        System.out.println("changing the 2nd match score/Done");


        MailSenderByBetRoundIdRequest finish = MailSenderByBetRoundIdRequest.builder().adminToken(adminToken).betroundId(betroundId).build();
        ValidatableResponse mailSender = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON).body(finish).put("/betrounds/finish").then();
        mailSender.statusCode(200);

        System.out.println("End game./Done");






    }





}
