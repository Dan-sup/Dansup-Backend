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
@Slf4j
@RequestMapping(value = "/danceclasses")
@Api(tags = "DanceClass API", description = "댄스 수업 관련 api")
public class DanceClassController {

    private final DanceClassService danceClassService;

    @ApiOperation(value = "Get DanceClassList", notes = "댄스 수업 리스트 조회")
    @GetMapping(value = "")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassList() {
        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

    // 검색어와 필터를 모두 적용하는 상황이 난감...
    @ApiOperation(value = "Create DanceClass", notes = "댄스 수업 정보 등록")
    @PostMapping(value = "")
    public ResponseEntity<Void> createDanceClass(@AuthenticationPrincipal User user,
                                                 @RequestBody @Valid CreateDanceClassDto createDanceClassDto) {

        // DanceClass Entity 생성 후
        // CreateDanceClassDto 에 담겨있는 영상을 S3에 올리고 ClassVideo Entity 생성

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Search DanceClass", notes = "검색어를 검색을 통한 댄스 수업 리스트 조회")
    @GetMapping("/searches")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassListBySearch(@RequestParam("word") String word) {

        //  2가지 로직 필요
        //  word는
        //  댄스 수업의 title 가능
        //  댄서 nickname 가능

        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

    @ApiOperation(value = "Filter DanceClass", notes = "필터로 걸러진 수업 리스트 조회")
    @GetMapping("/filters")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassListByFilter(@RequestBody @Valid DanceClassFilterDto danceClassFilterDto) {

        return ResponseEntity.ok(new ArrayList<GetDanceClassListDto>());
    }

    @ApiOperation(value = "Get DanceClass", notes = "댄스 수업 상세 조회")
    @GetMapping(value = "/{danceclassId}")
    public ResponseEntity<Optional<DanceClass>> getDanceClass(@AuthenticationPrincipal User user,
                                                              @PathVariable("danceclassId") Long danceclassId) {

        return ResponseEntity.ok(danceClassService.getClass(danceclassId));
    }

    @ApiOperation(value = "Get DanceClass Video", notes = "댄서 수업 영상 조회")
    @GetMapping(value = "/{danceclassId}/video")
    public ResponseEntity<GetFileUrlDto> getPortfolioVideoList(@PathVariable("danceclassId") Long danceclassId) {
        return ResponseEntity.ok(new GetFileUrlDto());
    }

//    @ApiOperation(value = "Modify DanceClass", notes = "댄스 수업 정보 수정")
//    @PutMapping(value = "/{danceclassId}")
//    public ResponseEntity<Void> updateDanceClass(@AuthenticationPrincipal User user,
//                                                 @RequestBody @Valid CreateDanceClassDto createDanceClassDto) {
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "Delete DanceClass", notes = "댄스 수업 삭제")
//    @DeleteMapping("/{danceclassId}")
//    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal User user,
//                                           @PathVariable Long danceclassId) {
//
//        // SOFT_DELETED 구현
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
