package com.dansup.server.api.user.service;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createOAuthUser(String email) {
        User user = User.builder()
                .email(email)
                .build();

        userRepository.save(user);

        return user;
    }
}
