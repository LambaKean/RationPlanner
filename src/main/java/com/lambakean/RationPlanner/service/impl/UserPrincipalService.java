package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.UserNotLoggedInException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.service.PrincipalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalService implements PrincipalService {

    @Override
    public boolean isPrincipalPresent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User;
    }

    @Override
    public Optional<User> getPrincipal() {

        if(!isPrincipalPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
    }

    @Override
    public Object getPrincipalOrElseThrowException(String exceptionMsg) {
        return getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException(exceptionMsg)
        );
    }
}
