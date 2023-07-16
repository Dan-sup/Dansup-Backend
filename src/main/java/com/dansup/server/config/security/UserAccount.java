package com.dansup.server.config.security;

import com.dansup.server.api.user.domain.User;
import lombok.Getter;

@Getter
public class UserAccount extends CustomUserDetails {
    private final User user;

    public UserAccount(User user) {
        super(user);
        this.user = user;
    }
}
