package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.Photo;
import com.lambakean.rationplanner.representation.dto.PhotoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDto toPhotoDto(Photo photo);
}
