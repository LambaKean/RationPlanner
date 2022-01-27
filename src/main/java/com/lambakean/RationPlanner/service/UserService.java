package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.dto.UserWithTokensDto;
import com.lambakean.RationPlanner.model.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserService {

    UserWithTokensDto register(@NonNull UserCredentialsDto userCredentialsDto);

    UserWithTokensDto login(@NonNull UserCredentialsDto userCredentialsDto);

    UserDto findUserById(@NonNull String id);

    Optional<User> findById(@NonNull String id);
}