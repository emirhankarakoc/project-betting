package com.betting.karakoc.account;

import com.betting.karakoc.repository.UserEntityRepository;
import com.betting.karakoc.security.TokenManager;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AllArgsConstructor
public class LoginTest {
    private final UserEntityRepository userRepository;
    private final TokenManager tokenManager;
    String pathLogin ="/account/login";






/*    @Test
    public void loginTest200Test(){
        UserEntity user = new UserEntity();

        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(a).post(pathLogin).then();
        then.statusCode(200);
    }*/
}
