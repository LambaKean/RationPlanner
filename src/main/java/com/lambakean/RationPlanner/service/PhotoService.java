package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.model.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo savePhoto(MultipartFile multipartFile);

    Resource loadPhoto(String filename);
}
