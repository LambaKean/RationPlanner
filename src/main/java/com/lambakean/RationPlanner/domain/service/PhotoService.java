package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo savePhoto(MultipartFile multipartFile);

    Resource loadPhoto(String filename);
}
