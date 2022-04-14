package com.lambakean.rationplanner.domain.service.impl;

import com.lambakean.rationplanner.data.model.User;
import com.lambakean.rationplanner.domain.service.PrincipalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    @Override
    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
