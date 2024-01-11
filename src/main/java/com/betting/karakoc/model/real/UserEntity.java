package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Entity@Data

public class UserEntity {
    @Id
    private String  id;
    private LocalDate createddatetime;
    private LocalDate updatedDateTime;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    @Enumerated
    private UserRole role;




    public static UserEntityDTO userToDto(UserEntity user){

        UserEntityDTO dto = new UserEntityDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        return dto;
    }



}
