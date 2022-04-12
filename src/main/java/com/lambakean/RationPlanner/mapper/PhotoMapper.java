package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.model.Photo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDto toPhotoDto(Photo photo);
}
