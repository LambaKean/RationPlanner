package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.SecurityTokensHolder;
import com.lambakean.rationplanner.representation.dto.SecurityTokensHolderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SecurityTokensHolderMapper {

    SecurityTokensHolderDto toSecurityTokensHolderDto(SecurityTokensHolder securityTokensHolder);
}
