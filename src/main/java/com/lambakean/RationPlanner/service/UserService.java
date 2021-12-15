package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import org.springframework.lang.NonNull;

public interface UserService {

    UserDto register(@NonNull UserCredentialsDto userCredentialsDto);

    UserDto login(@NonNull UserCredentialsDto userCredentialsDto);

    UserDto findUserById(@NonNull String id);
}