package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.model.PlannedDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannedDayRepository extends JpaRepository<PlannedDay, String> {}
