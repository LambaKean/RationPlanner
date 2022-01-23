package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PlannedDayRepository extends JpaRepository<PlannedDay, String> {

    Set<PlannedDay> findByUser(User user);

    boolean existsByIdAndUser(String id, User user);
}
