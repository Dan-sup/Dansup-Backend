package com.dansup.server.danceclass.controller;

import com.dansup.server.danceclass.domain.ClassVideo;
import com.dansup.server.danceclass.domain.DanceClass;
import com.dansup.server.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.danceclass.dto.request.DanceClassFilterDto;
import com.dansup.server.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.danceclass.service.DanceClassService;
import com.dansup.server.profile.dto.response.GetFileUrlDto;
import com.dansup.server.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/danceclasses")
@Api(tags = "DanceClass API", description = "댄스 수업 관련 api")
public class DanceClassController {

    private final DanceClassService danceClassService;

    @GetMapping("/sample")
    public String greeting(){
        return "sample!!";
    }

    @ApiOperation(value = "Get DanceClassList", notes = "댄스 수업 리스트 조회")
    @GetMapping(value = "")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassList() {
        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

    @ApiOperation(value = "Create DanceClass", notes = "댄스 수업 정보 등록")
    @PostMapping(value = "")
    public ResponseEntity<Void> createDanceClass(
                                                 @RequestBody CreateDanceClassDto createDanceClassDto) {

        // DanceClass Entity 생성 후
        // CreateDanceClassDto 에 담겨있는 영상을 S3에 올리고 ClassVideo Entity 생성

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @ApiOperation(value = "Search DanceClass", notes = "검색어를 검색을 통한 댄스 수업 리스트 조회")
//    @GetMapping("/searches")
//    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassListBySearch(@RequestParam("word") String word) {
//
//        //  2가지 로직 필요
//        //  word는
//        //  댄스 수업의 title 가능
//        //  댄서 nickname 가능
//
//        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
//    }

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
    public ResponseEntity<Optional<DanceClass>> getDanceClass(
                                                              @PathVariable("danceclassId") Long danceclassId) {

        return ResponseEntity.ok(danceClassService.getClass(danceclassId));
    }

    @ApiOperation(value = "Get DanceClass Video", notes = "댄서 수업 영상 조회")
    @GetMapping(value = "/{danceclassId}/video")
    public ResponseEntity<GetFileUrlDto> getPortfolioVideoList(@PathVariable("danceclassId") Long danceclassId) {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

    @ApiOperation(value = "Close DanceClass", notes = "댄스 수업 마감")
    @PutMapping(value = "/{danceclassId}")
    public ResponseEntity<Void> closeDanceClass(
                                                 @PathVariable("danceclassId") Long danceclassId) {
        // DanceClass 의 State 를 Closed 로 변경
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete DanceClass", notes = "댄스 수업 삭제")
    @DeleteMapping("/{danceclassId}")
    public ResponseEntity<Void> deletePost(
                                           @PathVariable("danceclassId") Long danceclassId) {

        // DanceClass 의 State 를 Deleted 로 변경
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
