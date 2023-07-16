package com.dansup.server.config.security;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.config.jwt.CustomUserDetails;
import lombok.Getter;

@Getter
public class UserAccount extends CustomUserDetails {
    private final User user;

    public UserAccount(User user) {
        super(user);
        this.user = user;
    }
}
