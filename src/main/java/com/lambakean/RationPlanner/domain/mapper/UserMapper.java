package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.User;
import com.lambakean.RationPlanner.representation.dto.UserDto;
import com.lambakean.RationPlanner.representation.dto.form.UserAuthenticationForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser (UserAuthenticationForm userAuthenticationForm);
}
