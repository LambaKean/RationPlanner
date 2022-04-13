package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.SecurityTokensHolder;
import com.lambakean.RationPlanner.representation.dto.SecurityTokensHolderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SecurityTokensHolderMapper {

    SecurityTokensHolderDto toSecurityTokensHolderDto(SecurityTokensHolder securityTokensHolder);
}
