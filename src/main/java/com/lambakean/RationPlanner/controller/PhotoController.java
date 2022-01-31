package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<PhotoDto> uploadPhoto(@RequestParam MultipartFile photo) {

        PhotoDto photoDto = photoService.savePhoto(photo);

        return new ResponseEntity<>(photoDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String id) {

        Resource resource = photoService.loadPhoto(id);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
