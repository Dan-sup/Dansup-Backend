package com.dansup.server.api.auth.controller;

import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.auth.service.AuthService;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.config.jwt.CustomUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Api(tags = "Auth API", description = "회원가입/로그인 api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "Sign Up", notes = "회원 가입")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> signUp(@AuthenticationPrincipal CustomUserDetails user, @RequestBody SignUpDto signUpDto) {
        log.info("[현재 로그인한 유저]: {}", user.getName());
        authService.signUp(user, signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
