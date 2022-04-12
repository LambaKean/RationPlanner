package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.model.SecurityTokensHolder;
import com.lambakean.RationPlanner.model.User;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface UserService {

    SecurityTokensHolder register(@NonNull User userData, HttpServletResponse httpServletResponse);

    SecurityTokensHolder login(@NonNull User userData, HttpServletResponse httpServletResponse);

    UserDto findUserById(@NonNull String id);

    Optional<User> findById(@NonNull String id);
}