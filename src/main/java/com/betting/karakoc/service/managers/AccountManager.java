package com.betting.karakoc.service.managers;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateUserRequest;
import com.betting.karakoc.repository.UserEntityRepository;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.security.TokenManager;
import com.betting.karakoc.service.interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.betting.karakoc.models.real.UserEntity.userToDto;
import static com.betting.karakoc.models.real.UserEntity.validateUsername;

@Service
@AllArgsConstructor
public class AccountManager implements AccountService {
    private final UserEntityRepository repository;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextUtil securityContextUtil;


    public UserEntityDTO register(CreateUserRequest request) {

        String username = request.getUsername();
        int specialCharCount = validateUsername(username);


        if (specialCharCount != 2 && username.length() < 7)
            throw new BadRequestException("Invalid username type.\nExample: example@example.com\nThe mail adress must be 8 digits or more.");

        if (repository.findAll().isEmpty()) {
            UserEntity user = new UserEntity();
            user.setId(UUID.randomUUID().toString());
            user.setUsername("jessuothebusiness@gmail.com");
            user.setPassword("admin");
            user.setRole(UserRole.ROLE_ADMIN);
            repository.save(user);
            UserEntity user2 = new UserEntity();
            user2.setId(UUID.randomUUID().toString());
            user2.setUsername("user");
            user2.setPassword("user");
            user2.setRole(UserRole.ROLE_USER);
            repository.save(user2);
        }
        if (repository.existsByusername(request.getUsername()))
            throw new BadRequestException("This mail is already taken.");
        if (!request.getPassword().equals(request.getRepeatPassword()))
            throw new BadRequestException("Password isn't matching.");
        // kimse yoksa admin ekle, username ayniysa ekleme, sifreler eslesmiyorsa olusturma.
        UserEntity dto = new UserEntity();
        dto.setId(UUID.randomUUID().toString());
        dto.setPassword(request.getPassword());
        dto.setFirstname(request.getFirstname());
        dto.setLastname(request.getLastname());
        dto.setUsername(request.getUsername());
        dto.setRole(UserRole.ROLE_USER);
        dto.setCreateddatetime(LocalDate.now());
        dto.setUpdatedDateTime(LocalDate.now());
        repository.save(dto);
        UserEntityDTO response = userToDto(dto);
        response.setMessage("CREATED SUCCESFULLY\n\n\n");
        response.setToken(tokenManager.generateToken(username));
        return response;
    }


    public String login(String mail, String password) {
        //token doncek. hosgeldin diyecek.

    }

    public UserEntityDTO getMe(String token) {
       // UserEntity user = securityContextUtil.getCurrentUser();
        //return userToDto(user);
        return new UserEntityDTO();
    }


}
