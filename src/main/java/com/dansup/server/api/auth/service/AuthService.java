package com.dansup.server.api.auth.service;

import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    public void signin(User user, SignUpDto signUpDto) {

        Profile profile = Profile.createProfile(signUpDto);

    }

}
