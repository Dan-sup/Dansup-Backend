package com.dansup.server.config.security;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[CustomUserDetailsServiceImpl] loadUserByUsername -> username: {}", username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));

        return new UserAccount(user);
    }

}
