package com.dansup.server.config.jwt.refresh;

import com.dansup.server.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60) // 단위는 초
public class RefreshToken extends BaseEntity {

    @Id
    @Indexed
    private String refreshToken;
    private String email;

    @Builder
    public RefreshToken(String refreshToken, String email) {
        this.refreshToken = refreshToken;
        this.email = email;
    }

}
