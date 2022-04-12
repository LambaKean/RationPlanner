package com.lambakean.RationPlanner.data.repository;

import com.lambakean.RationPlanner.data.model.PlannedDay;
import com.lambakean.RationPlanner.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PlannedDayRepository extends JpaRepository<PlannedDay, String> {

    Set<PlannedDay> findByUser(User user);

    boolean existsByIdAndUser(String id, User user);
}
