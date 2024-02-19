package com.betting.karakoc.account;


import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateUserRequest;
import com.betting.karakoc.repository.UserEntityRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static com.betting.karakoc.KarakocApplicationTests.API_URL;
import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class RegisterTest {

    String request_path = "/account/register";

    @Autowired
    private  UserEntityRepository userRepository;

    @PostConstruct
    public void init() {
        // Create a user with admin role
        UserEntity admin_user = new UserEntity();
        admin_user.setId(UUID.randomUUID().toString());
        admin_user.setFirstname("John");
        admin_user.setLastname("Doe");
        admin_user.setCreateddatetime(LocalDate.now());
        admin_user.setUsername("johndoe@gmail.com");
        admin_user.setPassword("doejohn98");
        admin_user.setRole(UserRole.ROLE_ADMIN);
        admin_user.setUpdatedDateTime(LocalDate.now());


        // Create a user with user role
        UserEntity normal_user = new UserEntity();
        normal_user.setId(UUID.randomUUID().toString());
        normal_user.setFirstname("John2");
        normal_user.setLastname("Doe2");
        normal_user.setCreateddatetime(LocalDate.now());
        normal_user.setUsername("johndoe2@gmail.com");
        normal_user.setPassword("doejohn987");
        normal_user.setRole(UserRole.ROLE_USER);
        normal_user.setUpdatedDateTime(LocalDate.now());
        userRepository.save(admin_user);
        userRepository.save(normal_user);
    }

    @Test
    public void register200Test(){
      CreateUserRequest eleman =  CreateUserRequest.builder()
                .firstname("EMIRHAN")
                .lastname("KARAKOC")
                .username("jessuothebusiness@gmail.com")
                .password("12345678")
                .repeatPassword("12345678")
                .build();

        ValidatableResponse then = given().baseUri(API_URL).accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(eleman).post(request_path).then();

        then.statusCode(200);
    }

}
