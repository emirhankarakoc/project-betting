package com.betting.karakoc.controller;

import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.UserRole;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.model.requests.CreateUserRequest;
import com.betting.karakoc.repository.UserEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bet/account")
@AllArgsConstructor
@Tag(name = "Account Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

public class AccountController {
    private final UserEntityRepository repository;

    @Operation(
            summary = "register to system.")
    @PostMapping("/register")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserEntityDTO register (CreateUserRequest request){
        String username = request.getUsername();
        int specialCharCount=0;
        for (int i = 0;i<username.length();i++){
            if (username.charAt(i) == '@') specialCharCount++;
            if (username.charAt(i)=='.') specialCharCount++;
            if (!username.endsWith(".com")) throw new GeneralException("Invalid username type.\nExample: example@example.com",400);
        }

        if (specialCharCount!=2 && username.length()<7) throw new GeneralException("Invalid username type.\nExample: example@example.com\nThe mail adress must be 8 digits or more.",400);

        if (repository.findAll().size()==0){UserEntity user = new UserEntity();user.setId(UUID.randomUUID().toString()); user.setUsername("admin");user.setPassword("admin");user.setRole(UserRole.ROLE_ADMIN); user.setToken("ADMINTOKEN"); repository.save(user);
            UserEntity user2 = new UserEntity();user2.setId(UUID.randomUUID().toString()); user2.setUsername("user");user2.setPassword("user");user2.setRole(UserRole.ROLE_USER); user2.setToken("USERTOKEN"); repository.save(user2);
        }
        if (repository.existsByUsername(request.getUsername())) throw new GeneralException("This username is already taken.",400);
        if(!request.getPassword().equals(request.getRepeatPassword())) throw new GeneralException("Password isn't matching.",400);
        // kimse yoksa admin ekle, username ayniysa ekleme, sifreler eslesmiyorsa olusturma.
        UserEntity dto = new UserEntity();dto.setId(UUID.randomUUID().toString());dto.setPassword(request.getPassword()); dto.setFirstname(request.getFirstname());dto.setLastname(request.getLastname());dto.setUsername(request.getUsername());dto.setRole(UserRole.ROLE_USER);dto.setCreateddatetime(LocalDate.now());dto.setUpdatedDateTime(LocalDate.now());dto.setToken(UUID.randomUUID().toString()); repository.save(dto);
        UserEntityDTO response = new UserEntityDTO();
        response.setMessage("CREATED SUCCESFULLY");
        response.setFirstname(dto.getFirstname());
        response.setUsername(dto.getUsername());
        response.setLastname(dto.getLastname());
        response.setPassword(dto.getPassword());
        response.setToken(dto.getToken());

        return response;
            }
    @Operation(
            summary = "login to system.")
    @PostMapping("/login")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public String login (String username, String password) {
        Optional<UserEntity> user = repository.findByUsername(username);
        if (user.isEmpty()) throw new GeneralException("Wrong username or password.",400);
        //check password
            if (user.get().getPassword().equals(password)){
                return user.get().getToken();
            }
        else throw new GeneralException("Wrong username or password.",400);
    }



}
