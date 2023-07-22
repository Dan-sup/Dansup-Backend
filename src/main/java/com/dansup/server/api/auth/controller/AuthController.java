package com.dansup.server.api.auth.controller;

import com.dansup.server.api.auth.dto.request.RefreshTokenDto;
import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.auth.dto.response.AccessTokenDto;
import com.dansup.server.api.auth.service.AuthService;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.AuthUser;
import com.dansup.server.common.response.Response;
import com.dansup.server.common.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Api(tags = "Auth API", description = "회원가입/로그인 api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "Sign Up", notes = "회원 가입")
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Void> signUp(@AuthUser User user,
                                 @RequestPart SignUpDto signUpDto,
                                 @RequestPart(required = false) MultipartFile profileImage,
                                 @RequestPart(required = false) MultipartFile profileVideo) throws IOException {

        log.info("[현재 로그인한 유저]: {}", user.getEmail());
        authService.signUp(user, signUpDto, profileImage, profileVideo);

        return Response.success(ResponseCode.SUCCESS_CREATED);
    }

    @ApiOperation(value = "Sign Out", notes = "로그아웃")
    @PostMapping(value = "/sign-out")
    public Response<Void> signOut(@AuthUser User user, @RequestBody RefreshTokenDto refreshTokenDto) {
        authService.signOut(user, refreshTokenDto);

        return Response.success(ResponseCode.SUCCESS_OK);
    }

    @ApiOperation(value = "Reissue Access Token by Refresh Token", notes = "리프레쉬 토큰을 통해 액세스 토큰 재발급")
    @PostMapping(value = "/reissuance")
    public Response<AccessTokenDto> reissueAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        AccessTokenDto accessTokenDto = authService.reissueAccessToken(refreshTokenDto);

        return Response.success(ResponseCode.SUCCESS_CREATED, accessTokenDto);
    }

}
