package com.lambakean.rationplanner.data.repository;

import com.lambakean.rationplanner.data.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String> {}
