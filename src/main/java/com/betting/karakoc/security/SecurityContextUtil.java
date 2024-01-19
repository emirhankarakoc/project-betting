package com.betting.karakoc.security;


import com.betting.karakoc.exceptions.general.ForbiddenException;
import com.betting.karakoc.model.real.UserEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {


    public UserEntity getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof AnonymousAuthenticationToken) {
            throw new ForbiddenException("Please log in.");
        }
        UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
