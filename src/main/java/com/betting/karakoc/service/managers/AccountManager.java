package com.betting.karakoc.service.managers;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateUserRequest;
import com.betting.karakoc.repository.UserEntityRepository;
import com.betting.karakoc.service.interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.betting.karakoc.models.real.UserEntity.*;

@Service
@AllArgsConstructor
public class AccountManager implements AccountService {
    private final UserEntityRepository repository;


    public UserEntityDTO register(CreateUserRequest request) {

        if (repository.existsByUsername(request.getUsername()))
            throw new BadRequestException("This mail is already taken.");
        validateUsername(request.getUsername());
        if (repository.findAll().isEmpty()) {
            UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), "KARAKOC", "KARAKOC", "ADMIN", "1", UserRole.ROLE_ADMIN, "1");
            repository.save(user);
            UserEntity user2 = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), "KARAKOC", "KARAKOC", "USER", "1", UserRole.ROLE_USER, "2");
            repository.save(user2);
        }
        UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), request.getFirstname(), request.getLastname(), request.getUsername(), passwordCrypter(request.getPassword()), UserRole.ROLE_USER, UUID.randomUUID().toString());
        repository.save(user);
        UserEntityDTO response = userToDto(user);
        response.setPassword(request.getPassword());
        response.setMessage("Your account is created successfully. Welcome MR/MRS. " + user.getFirstname());
        response.setToken(user.getToken());
        return response;
    }

    //  returns token
    public String login(String username, String password) {
        Optional<UserEntity> user = repository.findByUsername(username);

        //  if password is correct, return token.
        if (user.isPresent() && user.get().getPassword().equals(password)) {

            return user.get().getToken();
        }
        // if not, return exception.
        else {

            throw new BadRequestException("Username or password is wrong.");
        }
    }

    public UserEntityDTO getMe(String token) {
        Optional<UserEntity> user = repository.findByToken(token);
        return userToDto(user.get());
    }


}
