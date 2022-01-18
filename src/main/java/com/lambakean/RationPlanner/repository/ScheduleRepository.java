package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {}
