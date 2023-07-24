package com.dansup.server.api.profile.controller;

import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.profile.dto.response.GetProfileDto;
import com.dansup.server.api.profile.service.ProfileService;
import com.dansup.server.common.response.Response;
import com.dansup.server.common.response.ResponseCode;
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

    private final ProfileService profileService;
    @ApiOperation(value = "Get Dancer Profiles", notes = "댄서 검색 페이지에서 댄서 프로필 조회")
    @GetMapping(value = "")
    public Response<List<GetProfileDto>> getProfileList(@RequestParam("nickname") String nickname) {
        List<GetProfileDto> getProfileDtos = profileService.getProfileList(nickname);
        return Response.success(ResponseCode.SUCCESS_OK, getProfileDtos);
    }

    @ApiOperation(value = "Get Dancer Profile Detail", notes = "댄서 프로필 상세 조회")
    @GetMapping(value = "/{profileId}")
    public Response<GetProfileDetailDto> getProfileDetail(@PathVariable("profileId") Long profileId) {
        GetProfileDetailDto getProfileDetailDto = profileService.getProfile(profileId);
        return Response.success(ResponseCode.SUCCESS_OK, getProfileDetailDto);
    }

//    @ApiOperation(value = "Get Dancer Profile Video", notes = "댄서 프로필 대표 영상 조회")
//    @GetMapping(value = "/{profileId}/video")
//    public ResponseEntity<GetFileUrlDto> getProfileVideo(@PathVariable("profileId") Long profileId) {
//        return ResponseEntity.ok(new GetFileUrlDto());
//    }
//
//    @ApiOperation(value = "Get Dancer Profile Image", notes = "댄서 프로필 대표 이미지 조회")
//    @GetMapping(value = "/{profileId}/image")
//    public ResponseEntity<GetFileUrlDto> getProfileImage(@PathVariable("profileId") Long profileId) {
//        return ResponseEntity.ok(new GetFileUrlDto());
//    }

    @ApiOperation(value = "Get Dancer Portfolio", notes = "댄서 프로필에서 포트폴리오(공연 및 활동 경력) 조회")
    @GetMapping(value = "/{profileId}/portfolio")
    public Response<List<GetPortfolioDto>> getPortfolioList(@PathVariable("profileId") Long profileId) {
        List<GetPortfolioDto> getPortfolioDtos = profileService.getPortfolioList(profileId);
        return Response.success(ResponseCode.SUCCESS_OK, getPortfolioDtos);
    }

    @ApiOperation(value = "Get Dancer Portfolio Videos", notes = "댄서 프로필에서 포트폴리오 영상 조회")
    @GetMapping(value = "/{profileId}/portfolio/video")
    public Response<List<GetFileUrlDto>> getPortfolioVideoList(@PathVariable("profileId") Long profileId) {
        List<GetFileUrlDto> getFileUrlDtos = profileService.getPortfolioVideoList(profileId);
        return Response.success(ResponseCode.SUCCESS_OK, getFileUrlDtos);
    }

    @ApiOperation(value = "Get Dancer Classes", notes = "댄서 프로필에서 운영 중인 수업 조회")
    @GetMapping(value = "/{profileId}/class")
    public Response<List<GetDanceClassListDto>> getProfileClassList(@PathVariable("profileId") Long profileId) {
        List<GetDanceClassListDto> getDanceClassListDtos = profileService.getDanceClassList(profileId);
        return Response.success(ResponseCode.SUCCESS_OK, getDanceClassListDtos);
    }

}
