package com.dansup.server;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.domain.UserRole;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.api.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class SignUpTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void getUserProfile() {
        userService.createOAuthUser("test@test.com");

        User user = userRepository.findByEmail("test@test.com").get();

        assertEquals(user.getUserRole(), UserRole.ROLE_GUEST);

    }
}
