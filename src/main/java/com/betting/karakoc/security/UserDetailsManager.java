package com.betting.karakoc.security;

import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.model.enums.UserRole;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.repository.UserEntityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    private final UserEntityRepository userRepository;

    @PostConstruct
    public void init() {
        List<UserEntity> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setId(UUID.randomUUID().toString());
            admin.setUsername("jessuothebusiness@gmail.com");
            admin.setFirstname("EMIRHAN6");
            admin.setRole(UserRole.ROLE_ADMIN);
            admin.setPassword("1");
            userRepository.save(admin);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        UserEntity user = optionalUser.orElseThrow(() -> new NotfoundException("User not found."));

        return new UserCustomDetails(user);
    }
}
