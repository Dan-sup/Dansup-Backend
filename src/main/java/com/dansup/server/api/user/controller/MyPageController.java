package com.dansup.server.api.user.controller;

import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.service.DanceClassService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
@Api(tags = "My Page API", description = "마이페이지 관련 api")
public class MyPageController {

    private final MyPageService myPageService;
    private final DanceClassService danceClassService;

    @ApiOperation(value = "Get Mypage", notes = "마이페이지 조회")
    @GetMapping(value = "")
    public Response<GetProfileDetailDto> getMyPage(@AuthUser User user) {
        GetProfileDetailDto getProfileDetailDto = myPageService.getMyPage(user);
        return Response.success(ResponseCode.SUCCESS_OK, getProfileDetailDto);
    }

    @ApiOperation(value = "Get Mypage Portfolio", notes = "마이페이지에서 포트폴리오(공연 및 활동 경력) 조회")
    @GetMapping(value = "/portfolio")
    public Response<List<GetPortfolioDto>> getPortfolioList(@AuthUser User user) {
        List<GetPortfolioDto> getPortfolioDtos = myPageService.getPortfolioList(user);
        return Response.success(ResponseCode.SUCCESS_OK, getPortfolioDtos);
    }

    @ApiOperation(value = "Get Mypage Portfolio Videos", notes = "마이페이지에서 포트폴리오 영상 조회")
    @GetMapping(value = "/portfolio/video")
    public Response<List<GetFileUrlDto>> getPortfolioVideoList(@AuthUser User user) {
        List<GetFileUrlDto> getFileUrlDtos = myPageService.getPortfolioVideoList(user);
        return Response.success(ResponseCode.SUCCESS_OK, getFileUrlDtos);
    }

    @ApiOperation(value = "Get My Classes", notes = "마이페이지에서 운영 중인 수업 조회")
    @GetMapping(value = "/class")
    public Response<List<GetDanceClassListDto>> getProfileClassList(@AuthUser User user) {
        List<GetDanceClassListDto> getDanceClassListDtos = myPageService.getDanceClassList(user);
        return Response.success(ResponseCode.SUCCESS_OK, getDanceClassListDtos);
    }

    @ApiOperation(value = "Create DanceClass", notes = "댄스 수업 정보 등록")
    @PostMapping(value = "/class",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Void> createDanceClass(@RequestPart(value="createDanceClassDto", required = false) CreateDanceClassDto createDanceClassDto,
                                           @RequestPart(value="videoFile", required = false) MultipartFile videoFile,
                                           @RequestPart(value="thumbnail", required = false) MultipartFile thumbnail,
                                           @AuthUser User user) throws IOException {

        danceClassService.createClass(user, createDanceClassDto, videoFile, thumbnail);

        return Response.success(ResponseCode.SUCCESS_CREATED);
    }

    @ApiOperation(value = "Close DanceClass", notes = "댄스 수업 마감")
    @PutMapping(value = "/class/{danceclassId}")
    public Response<Void> closeDanceClass( @AuthUser User user,
                                           @PathVariable("danceclassId") Long danceclassId) {
        // DanceClass 의 State 를 Closed 로 변경
        danceClassService.closeClass(user, danceclassId);

        return Response.success(ResponseCode.SUCCESS_OK);
    }

    @ApiOperation(value = "Delete DanceClass", notes = "댄스 수업 삭제")
    @DeleteMapping("/class/{danceclassId}")
    public Response<Void> deletePost(@AuthUser User user,
                                     @PathVariable("danceclassId") Long danceclassId) {

        // DanceClass 의 State 를 Deleted 로 변경
        danceClassService.deleteClass(user, danceclassId);

        return Response.success(ResponseCode.SUCCESS_OK);
    }
}

