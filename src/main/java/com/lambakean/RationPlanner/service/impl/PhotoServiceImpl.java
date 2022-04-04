package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.dto.converter.PhotoDtoConverter;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Photo;
import com.lambakean.RationPlanner.repository.PhotoRepository;
import com.lambakean.RationPlanner.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final Set<String> PHOTO_CONTENT_TYPES = new HashSet<>();

    {
        PHOTO_CONTENT_TYPES.add("image/jpeg");
        PHOTO_CONTENT_TYPES.add("image/png");
    }

    private final PhotoRepository photoRepository;
    private final PhotoDtoConverter photoDtoConverter;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository,
                            PhotoDtoConverter photoDtoConverter) {
        this.photoRepository = photoRepository;
        this.photoDtoConverter = photoDtoConverter;
    }

    @Override
    public PhotoDto savePhoto(MultipartFile multipartFile) {

        if(!PHOTO_CONTENT_TYPES.contains(multipartFile.getContentType())) {
            throw new BadRequestException("Фотография должна иметь расширение jpg, jpeg, png, jpe или jfif");
        }

        Photo photo = new Photo();
        try {
            photo.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("Фотография, которую вы выбрали, битая");
        }

        photoRepository.saveAndFlush(photo);

        return photoDtoConverter.toPhotoDto(photo);
    }

    @Override
    @Transactional
    public Resource loadPhoto(String id) {

        byte[] resource = photoRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Фотография с id \"%s\" не найдена", id))
                )
                .getContent();

        return new ByteArrayResource(resource);
    }
}
