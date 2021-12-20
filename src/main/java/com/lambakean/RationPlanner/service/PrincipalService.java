package com.lambakean.RationPlanner.service;

import java.security.Principal;

public interface PrincipalService {

    boolean isPrincipalPresent();

    Object getCurrentPrincipal();
}
