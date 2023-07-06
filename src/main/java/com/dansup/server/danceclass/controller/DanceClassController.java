package com.dansup.server.danceclass.controller;

import com.dansup.server.danceclass.domain.DanceClass;
import com.dansup.server.danceclass.dto.CreateDanceClassReqDto;
import com.dansup.server.danceclass.dto.CreateDanceClassResDto;
import com.dansup.server.danceclass.dto.GetDanceClassDto;
import com.dansup.server.danceclass.dto.GetDanceClassListDto;
import com.dansup.server.danceclass.service.DanceClassService;
import com.dansup.server.user.domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DanceClassController {

    private final DanceClassService danceClassService;

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 리스트 출력")
    @GetMapping(value = "/danceclasses")
    public ResponseEntity<List<GetDanceClassListDto>> getAllDanceClassList() {
        
        List<GetDanceClassListDto> danceClassListDto = null;
    
        return ResponseEntity.ok(danceClassListDto);
    }

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 정보 등록")
    @PostMapping(value = "/danceclasses")
    public ResponseEntity<CreateDanceClassResDto> createDanceClass(@AuthenticationPrincipal User user,
                                                                   @RequestBody @Valid CreateDanceClassReqDto createDanceClassReqDto) {

        return ResponseEntity.ok(new CreateDanceClassResDto());
    }

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 검색을 통한 수업 리스트 반환")
    @GetMapping("/danceclasses/search")
    public ResponseEntity<List<GetDanceClassListDto>> getDanceClassListByTitle(@RequestParam("title") String title) {

        List<GetDanceClassListDto> danceClassListDto = null;

        return ResponseEntity.ok(danceClassListDto);
    }

    @ApiOperation(value = "DanceClass",
            notes = "운영중인 수업 리스트 반환")
    @GetMapping("/danceclasses/user")
    public ResponseEntity<List<GetDanceClassListDto>> getAllDanceClassListByUser(@AuthenticationPrincipal User user) {

        List<GetDanceClassListDto> danceClassListDto = null;

        return ResponseEntity.ok(danceClassListDto);
    }

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 정보 반환")
    @GetMapping(value = "/danceclasses/{danceclassId}")
    public ResponseEntity<Optional<DanceClass>> getDanceClass(@AuthenticationPrincipal User user,
                                                              @PathVariable("danceclassId") Long danceclassId) {

        return ResponseEntity.ok(danceClassService.getClass(danceclassId));
    }

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 정보 수정")
    @PutMapping(value = "/danceclasses/{danceclassId}")
    public ResponseEntity<CreateDanceClassResDto> updateDanceClass(@AuthenticationPrincipal User user,
                                                                   @RequestBody @Valid CreateDanceClassReqDto createDanceClassReqDto) {

        return ResponseEntity.ok(new CreateDanceClassResDto());
    }

    @ApiOperation(value = "DanceClass",
            notes = "댄스 수업 삭제")
    @DeleteMapping("/danceclasses/{danceclassId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long danceclassId, @AuthenticationPrincipal User user) {

        // SOFT_DELETED 구현

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
