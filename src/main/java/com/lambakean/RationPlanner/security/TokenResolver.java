package com.lambakean.RationPlanner.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenResolver {

    Optional<String> resolveToken(HttpServletRequest httpServletRequest);
}
