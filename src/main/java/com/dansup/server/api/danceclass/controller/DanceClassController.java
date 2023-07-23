package com.dansup.server.api.danceclass.controller;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.request.DanceClassFilterDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.service.DanceClassService;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.AuthUser;
import com.dansup.server.common.response.Response;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.s3.S3UploaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/danceclasses")
@Api(tags = "DanceClass API", description = "댄스 수업 관련 api")
@Slf4j
public class DanceClassController {

    private final DanceClassService danceClassService;
    private final S3UploaderService s3UploaderService;

//    test api
//    @RequestMapping("/sample")
//    public String greeting(){
//        return "sample!!";
//    }

    //test api
    //image 업로드 및 주소 반환 test
    @PostMapping("/image-upload")
    @ResponseBody
    public String imageUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "dansupbucket", "image");
    }

    //test api
    //video 업로드 및 주소 반환 test
    @PostMapping("/video-upload")
    @ResponseBody
    public String videoUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "dansupbucket", "video");
    }

    @ApiOperation(value = "Get DanceClassList", notes = "댄스 수업 리스트 조회")
    @GetMapping(value = "")
    public Response<List<GetDanceClassListDto>> getDanceClassList(@AuthUser User user) {
        List<GetDanceClassListDto> danceClassListDto = danceClassService.getAllClassList(user);
        Collections.reverse(danceClassListDto);

        return Response.success(ResponseCode.SUCCESS_OK, danceClassListDto);
    }

    @ApiOperation(value = "Create DanceClass", notes = "댄스 수업 정보 등록")
    @PostMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Void> createDanceClass(      @RequestPart(value="createDanceClassDto", required = false) CreateDanceClassDto createDanceClassDto,
                                                 @RequestPart(value="videoFile", required = false) MultipartFile videoFile,
                                                 @RequestPart(value="thumbnail", required = false) MultipartFile thumbnail,
                                                 @AuthUser User user) throws IOException {

        log.info("[요청 유저]: {}", user.getEmail());
        danceClassService.createClass(user, createDanceClassDto, videoFile, thumbnail);

        return Response.success(ResponseCode.SUCCESS_CREATED);
    }

    @ApiOperation(value = "Filter DanceClass", notes = "필터링(검색 or 필터)을 통한 수업 리스트 조회")
    @GetMapping("/filters")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassListByFilter(@RequestParam("word") String word,
                                                                                @RequestBody DanceClassFilterDto danceClassFilterDto) {
        //  (1) 검색어(word)
        //  word 검색시 2가지 로직 필요
        //  word는
        //  댄스 수업의 title 로 Filtering
        //  댄서 nickname 로 Filtering

        // (2) FilterDto
        //  FilterDto를 통해 Filtering
        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

    @ApiOperation(value = "Get DanceClass", notes = "댄스 수업 상세 조회")
    @GetMapping(value = "/{danceclassId}")
    public Response<GetDanceClassDto> getDanceClass(@AuthUser User user,
                                                    @PathVariable("danceclassId") Long danceclassId) {

        GetDanceClassDto getDanceClassDto = danceClassService.detailClass(user, danceclassId);
        return Response.success(ResponseCode.SUCCESS_OK, getDanceClassDto);
    }

    @ApiOperation(value = "Close DanceClass", notes = "댄스 수업 마감")
    @PutMapping(value = "/{danceclassId}")
    public Response<Void> closeDanceClass( @AuthUser User user,
                                                 @PathVariable("danceclassId") Long danceclassId) {
        // DanceClass 의 State 를 Closed 로 변경
        danceClassService.closeClass(user, danceclassId);

        return Response.success(ResponseCode.SUCCESS_OK);
    }

    @ApiOperation(value = "Delete DanceClass", notes = "댄스 수업 삭제")
    @DeleteMapping("/{danceclassId}")
    public Response<Void> deletePost(@AuthUser User user,
                                           @PathVariable("danceclassId") Long danceclassId) {

        // DanceClass 의 State 를 Deleted 로 변경
        danceClassService.deleteClass(user, danceclassId);

        return Response.success(ResponseCode.SUCCESS_OK);
    }
}
