package com.dansup.server.api.user.controller;

import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.dto.request.UploadFileDto;
import com.dansup.server.api.user.service.MyPageService;
import com.dansup.server.common.AuthUser;
import com.dansup.server.common.response.Response;
import com.dansup.server.common.response.ResponseCode;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
@Api(tags = "My Page API", description = "마이페이지 관련 api")
public class MyPageController {

    private final MyPageService myPageService;

    @ApiOperation(value = "Get Mypage", notes = "마이페이지 조회")
    @GetMapping(value = "")
    public Response<GetProfileDetailDto> getMyPage(@AuthUser User user) {
        GetProfileDetailDto getProfileDetailDto = myPageService.getMyPage(user);
        return Response.success(ResponseCode.SUCCESS_OK, getProfileDetailDto);
    }

    @ApiOperation(value = "Get Mypage Video", notes = "마이페이지 대표 영상 조회")
    @GetMapping(value = "/video")
    public ResponseEntity<GetFileUrlDto> getProfileVideo() {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

    @ApiOperation(value = "Get Mypage Image", notes = "마이페이지 대표 이미지 조회")
    @GetMapping(value = "/image")
    public ResponseEntity<GetFileUrlDto> getProfileImage() {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

    @ApiOperation(value = "Get Mypage Portfolio", notes = "마이페이지에서 포트폴리오(공연 및 활동 경력) 조회")
    @GetMapping(value = "/portfolio")
    public ResponseEntity<List<GetPortfolioDto>> getPortfolioList() {
        return ResponseEntity.ok(new ArrayList<GetPortfolioDto>());
    }

    @ApiOperation(value = "Get Mypage Portfolio Videos", notes = "마이페이지에서 포트폴리오 영상 조회")
    @GetMapping(value = "/portfolio/video")
    public ResponseEntity<List<GetFileUrlDto>> getPortfolioVideoList() {
        return ResponseEntity.ok(new ArrayList<GetFileUrlDto>());
    }

    @ApiOperation(value = "Upload Mypage Portfolio Videos", notes = "마이페이지에서 포트폴리오 영상 업로드")
    @PostMapping(value = "/portfolio/video")
    public ResponseEntity<Void> getPortfolioVideoList(@RequestBody UploadFileDto uploadFileDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get My Classes", notes = "마이페이지에서 운영 중인 수업 조회")
    @GetMapping(value = "/class")
    public ResponseEntity<List<GetDanceClassListDto>> getProfileClassList() {
        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

}

