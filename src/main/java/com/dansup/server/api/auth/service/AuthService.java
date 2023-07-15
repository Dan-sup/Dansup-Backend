package com.dansup.server.api.auth.service;

import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.domain.UserRole;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.exception.ExceptionCode;
import com.dansup.server.config.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public void signUp(User user, SignUpDto signUpDto) {
        // TO DO: 파일 업로드 로직 추가해야함
        Profile profile = Profile.createProfile(user, signUpDto);
        profileRepository.save(profile);
        user.updateUserRole(UserRole.ROLE_USER);

        log.info("[Sign Up 완료]: {}, {}", profile.getId(), user.getUserRole());
    }

}
