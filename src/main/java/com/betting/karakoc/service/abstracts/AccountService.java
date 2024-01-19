package com.betting.karakoc.service.abstracts;

import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.requests.CreateUserRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    UserEntityDTO register(CreateUserRequest request);

    ResponseEntity<String> login(String username, String password);

    public UserEntityDTO getMe();
}
