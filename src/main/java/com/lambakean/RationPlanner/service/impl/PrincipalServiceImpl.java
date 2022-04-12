package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.service.PrincipalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    @Override
    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
