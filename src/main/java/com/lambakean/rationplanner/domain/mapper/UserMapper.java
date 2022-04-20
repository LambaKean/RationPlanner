package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.User;
import com.lambakean.rationplanner.representation.dto.UserDto;
import com.lambakean.rationplanner.representation.dto.form.UserAuthenticationForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser (UserAuthenticationForm userAuthenticationForm);
}
