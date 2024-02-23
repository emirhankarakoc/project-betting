package com.betting.karakoc.service.managers;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateManagerRequest;
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
    private final UserEntityRepository userRepository;
    Random random;


    public UserEntityDTO register(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new BadRequestException("This mail is already taken.");
        validateUsername(request.getUsername());
        if (userRepository.findAll().isEmpty()) {
      //      UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), "KARAKOC", "KARAKOC", "ADMIN", "1", UserRole.ROLE_ADMIN, "1");
       //     repository.save(user);
       //     UserEntity user2 = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), "KARAKOC", "KARAKOC", "USER", "1", UserRole.ROLE_USER, "2");
      //      repository.save(user2);
        }
//        UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), request.getFirstname(), request.getLastname(), request.getUsername(), passwordCrypter(request.getPassword()), UserRole.ROLE_USER, UUID.randomUUID().toString());
//        repository.save(user);
//        UserEntityDTO response = userToDto(user);
//        response.setPassword(request.getPassword());
//        response.setMessage("Your account is created successfully. Welcome MR/MRS. " + user.getFirstname());
//        response.setToken(user.getToken());
//        return response;
        throw new BadRequestException("");
    }

    //  returns token
    public String login(String username, String password) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

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
        Optional<UserEntity> user = userRepository.findByToken(token);
        return userToDto(user.get());
    }

    public UserEntityDTO register1(CreateUserRequest request) {
        Optional<UserEntity> checkCreatorCodeIsValid = userRepository.findByCreatorCode(request.getCreator_code());
        //this control checks, is someone has this creatorcode? if someone has-> its good.
        //if someone hasnt this creator code... go exception.


        if (checkCreatorCodeIsValid.isPresent()) {
            UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), request.getFirstname(), request.getLastname(), request.getUsername(), request.getPassword(), UserRole.ROLE_USER, UUID.randomUUID().toString(), request.getCreator_code());
            UserEntityDTO dto = userToDto(user);
            userRepository.save(user);
            return dto;
        }
        else{
            throw new BadRequestException("Invalid creator code.");
        }
    }
    public UserEntityDTO register2(CreateManagerRequest request){
        int creatorCode = random.nextInt(2000)+1;
        Optional<UserEntity> checkCreatorCodeIsValid = userRepository.findByCreatorCode(creatorCode);
        //this control checks, is someone has this creatorcode? if someone has-> go create again.
        // if someone hasnt, use as creatorcode.

        if(checkCreatorCodeIsValid.isPresent()){registerManager(request);} //recursive.
        else {
            UserEntity user = new UserEntity(UUID.randomUUID().toString(), LocalDate.now(), LocalDate.now(), request.getFirstname(), request.getLastname(), request.getUsername(), request.getPassword(), UserRole.ROLE_USER, UUID.randomUUID().toString(), creatorCode);

            UserEntityDTO dto = userToDto(user);
            userRepository.save(user);
            return dto;
        }
        throw new BadRequestException("Something went wrong, errorcode: rm-cmr-61");
    }
}
