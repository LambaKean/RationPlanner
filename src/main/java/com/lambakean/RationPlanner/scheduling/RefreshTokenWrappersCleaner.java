package com.lambakean.RationPlanner.scheduling;

import com.lambakean.RationPlanner.repository.RefreshTokenWrapperRepository;
import com.lambakean.RationPlanner.security.authentication.RefreshTokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefreshTokenWrappersCleaner {

    private final RefreshTokenWrapperRepository refreshTokenWrapperRepository;

    @Autowired
    public RefreshTokenWrappersCleaner(RefreshTokenWrapperRepository refreshTokenWrapperRepository) {
        this.refreshTokenWrapperRepository = refreshTokenWrapperRepository;
    }

    /**
     * Удаляет из базы данных просроченные refresh токены
     */
    @Scheduled(fixedDelay = 172800000)
    public void removeExpiredRefreshTokens() {

        List<RefreshTokenWrapper> refreshTokenWrappers = refreshTokenWrapperRepository.findAll();

        for(RefreshTokenWrapper refreshTokenWrapper : refreshTokenWrappers) {
            if(!refreshTokenWrapper.isTokenValid()) {
                refreshTokenWrapperRepository.delete(refreshTokenWrapper);
            }
        }
    }
}
