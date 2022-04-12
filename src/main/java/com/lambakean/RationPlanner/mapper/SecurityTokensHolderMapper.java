package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.SecurityTokensHolderDto;
import com.lambakean.RationPlanner.model.SecurityTokensHolder;
import org.mapstruct.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring")
public interface SecurityTokensHolderMapper {

    SecurityTokensHolderDto toSecurityTokensHolderDto(SecurityTokensHolder securityTokensHolder);
}
