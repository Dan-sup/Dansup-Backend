package com.dansup.server.profile.controller;

import com.dansup.server.profile.dto.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile")
@Api(tags = "Profile API", description = "유저 프로필 관련 api")
public class ProfileController {

    @ApiOperation(value = "Get Dancer Profiles", notes = "댄서 검색 페이지에서 댄서 프로필 조회")
    @GetMapping(value = "")
    public ResponseEntity<List<GetProfileDto>> getProfileList(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(new ArrayList<GetProfileDto>());
    }

    @ApiOperation(value = "Get Dancer Profile Detail", notes = "댄서 프로필 상세 조회")
    @GetMapping(value = "/{profileId}")
    public ResponseEntity<GetProfileDetailDto> getProfileDetail(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new GetProfileDetailDto());
    }

    @ApiOperation(value = "Get Dancer Profile Video", notes = "댄서 프로필 대표 영상 조회")
    @GetMapping(value = "/{profileId}/video")
    public ResponseEntity<GetFileUrlDto> getProfileVideo(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

    @ApiOperation(value = "Get Dancer Profile Image", notes = "댄서 프로필 대표 이미지 조회")
    @GetMapping(value = "/{profileId}/image")
    public ResponseEntity<GetFileUrlDto> getProfileImage(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

    @ApiOperation(value = "Get Dancer Portfolio", notes = "댄서 프로필에서 포트폴리오(공연 및 활동 경력) 조회")
    @GetMapping(value = "/{profileId}/portfolio")
    public ResponseEntity<List<GetPortfolioDto>> getPortfolioList(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new ArrayList<GetPortfolioDto>());
    }

    @ApiOperation(value = "Get Dancer Portfolio Videos", notes = "댄서 프로필에서 포트폴리오 영상 조회")
    @GetMapping(value = "/{profileId}/portfolio/video")
    public ResponseEntity<List<GetFileUrlDto>> getPortfolioVideoList(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new ArrayList<GetFileUrlDto>());
    }

    @ApiOperation(value = "Get Dancer Classes", notes = "댄서 프로필에서 운영 중인 수업 조회")
    @GetMapping(value = "/{profileId}/class")
    public ResponseEntity<List<GetProfileClassListDto>> getProfileClassList(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new ArrayList<GetProfileClassListDto>());
    }

}
