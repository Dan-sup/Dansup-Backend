package com.dansup.server.config.jwt.refresh;

import com.dansup.server.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .email(email)
                .build();

        refreshTokenRepository.save(refreshToken);

        log.info("[로그인 후 Refresh Token 저장]: {}", token);

    }
}
