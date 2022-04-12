package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.form.UserAuthenticationForm;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser (UserAuthenticationForm userAuthenticationForm);
}
