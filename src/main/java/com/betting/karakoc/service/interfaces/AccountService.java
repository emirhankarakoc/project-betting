package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.requests.CreateUserRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    UserEntityDTO register(CreateUserRequest request);

    String login(String username, String password);

    public UserEntityDTO getMe(String token);
}
