package com.lambakean.RationPlanner.data.repository;

import com.lambakean.RationPlanner.data.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String> {}
