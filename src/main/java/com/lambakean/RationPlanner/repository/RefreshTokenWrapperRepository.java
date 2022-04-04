package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.security.authentication.RefreshTokenWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenWrapperRepository extends JpaRepository<RefreshTokenWrapper, String> {

    Optional<RefreshTokenWrapper> findByToken(String token);

    boolean existsByToken(String token);
}
