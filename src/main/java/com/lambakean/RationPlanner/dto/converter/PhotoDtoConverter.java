package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.model.Photo;
import com.lambakean.RationPlanner.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoDtoConverter {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoDtoConverter(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo toPhoto(PhotoDto photoDto) {

        if(photoDto == null || photoDto.getId() == null && photoDto.getExtension() == null) {
            return null;
        }

        if(photoDto.getId() != null) {
            return photoRepository.findById(photoDto.getId()).orElse(null);
        }

        Photo photo = new Photo();
        photo.setExtension(photoDto.getExtension());

        return photo;
    }

    public PhotoDto toPhotoDto(Photo photo) {

        if(photo == null) {
            return null;
        }

        return new PhotoDto(photo.getId(), photo.getExtension());
    }
}
