package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.dto.converter.PhotoDtoConverter;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.FileNotFoundException;
import com.lambakean.RationPlanner.model.Photo;
import com.lambakean.RationPlanner.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Component
public class PhotoServiceImpl implements PhotoService {

    private final Set<String> PHOTO_CONTENT_TYPES = Set.of("image/jpeg", "image/png");
    private final Path UPLOADS_ROOT = Paths.get("photos");

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

        String filename = multipartFile.getOriginalFilename();
        if(filename == null) {
            throw new BadRequestException("Имя загруженной фотографии невалидно");
        }

        String fileExtension = filename.substring(filename.lastIndexOf('.') + 1);

        Photo photo = new Photo();
        photo.setExtension(fileExtension);

        photoRepository.saveAndFlush(photo);

        try {
            Files.copy(multipartFile.getInputStream(), UPLOADS_ROOT.resolve(photo.getId() + "." + fileExtension));
        } catch (IOException e) {
            photoRepository.delete(photo);
            throw new BadRequestException("Загруженная вами фотография повреждена и не может быть сохранена");
        }

        return photoDtoConverter.toPhotoDto(photo);
    }

    @Override
    public Resource loadPhoto(String filename) {

        Path file = UPLOADS_ROOT.resolve(filename);

        Resource resource;

        try {
            resource = new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new BadRequestException("Что-то пошло не так");
        }

        if(resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Запрошенный вами файл не был найден");
        }
    }
}
