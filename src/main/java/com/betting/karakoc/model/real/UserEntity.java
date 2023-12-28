package com.betting.karakoc.model.real;


import com.betting.karakoc.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

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
    private String token;
    @Enumerated
    private UserRole role;

}
