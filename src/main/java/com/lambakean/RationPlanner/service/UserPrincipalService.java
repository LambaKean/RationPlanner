package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipalService implements PrincipalService {

    @Override
    public boolean isPrincipalPresent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User;
    }

    @Override
    public User getCurrentPrincipal() {

        if(isPrincipalPresent()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return null;
    }
}
