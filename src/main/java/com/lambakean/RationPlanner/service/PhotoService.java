package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PhotoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    PhotoDto savePhoto(MultipartFile multipartFile);

    Resource loadPhoto(String filename);
}
