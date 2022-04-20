package com.lambakean.rationplanner.data.repository;

import com.lambakean.rationplanner.data.model.PlannedDay;
import com.lambakean.rationplanner.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PlannedDayRepository extends JpaRepository<PlannedDay, String> {

    Set<PlannedDay> findByUser(User user);

    boolean existsByIdAndUser(String id, User user);
}
