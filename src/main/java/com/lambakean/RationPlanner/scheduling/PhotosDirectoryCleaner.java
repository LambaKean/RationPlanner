package com.lambakean.RationPlanner.scheduling;

import com.lambakean.RationPlanner.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class PhotosDirectoryCleaner {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotosDirectoryCleaner(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Scheduled(fixedDelay = 172800000)
    public void removeUnusedPhotos() {

        Set<File> unusedFiles = new HashSet<>();

        Stream.of(Objects.requireNonNull(new File("photos").listFiles()))
                .forEach(file -> {

                    String filename = file.getName();
                    String photoId = filename.substring(0, filename.lastIndexOf('.'));

                    if(!photoRepository.existsById(photoId)) {
                        unusedFiles.add(file);
                    }
                });

        for(File file : unusedFiles) {
            if(!file.delete()) {
                throw new RuntimeException("Не удалось удалить файл " + file.getAbsolutePath());
            }
        }
    }
}
