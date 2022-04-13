package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.Photo;
import com.lambakean.RationPlanner.representation.dto.PhotoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDto toPhotoDto(Photo photo);
}
