package com.lambakean.RationPlanner.service;

import java.util.Optional;

public interface PrincipalService {

    boolean isPrincipalPresent();

    Optional<?> getCurrentPrincipal();
}
