package com.lambakean.RationPlanner.domain.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenResolver {

    Optional<String> resolveToken(HttpServletRequest httpServletRequest);
}
