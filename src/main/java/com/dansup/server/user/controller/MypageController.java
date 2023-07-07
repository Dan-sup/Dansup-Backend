package com.dansup.server.user.controller;

import com.dansup.server.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.user.dto.request.UploadFileDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dansup.server.profile.dto.response.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
@Api(tags = "Mypage API", description = "마이페이지 관련 api")
public class MypageController {

    @ApiOperation(value = "Get Mypage", notes = "마이페이지 조회")
    @GetMapping(value = "")
    public ResponseEntity<GetProfileDetailDto> getMyPage() {
        return ResponseEntity.ok(new GetProfileDetailDto());
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

