package com.dansup.server.profile.controller;

import com.dansup.server.profile.dto.response.GetFileUrlDto;
import com.dansup.server.profile.dto.response.GetPortfolioDto;
import com.dansup.server.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.profile.dto.response.GetProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile")
public class ProfileController {

    @GetMapping(value = "")
    public ResponseEntity<List<GetProfileDto>> getProfile(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(new ArrayList<GetProfileDto>());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<GetProfileDetailDto> getProfileDetail(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new GetProfileDetailDto());
    }

    @GetMapping(value = "/{userId}/portfolio")
    public ResponseEntity<List<GetPortfolioDto>> getPortfolio(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new ArrayList<GetPortfolioDto>());
    }

    @GetMapping(value = "/profile/{userId}/portfolio/video")
    public ResponseEntity<List<GetFileUrlDto>> getPortfolioVideo(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new ArrayList<GetFileUrlDto>());
    }

    @PostMapping(value = "/profile/{userId}/portfolio/video")
    public ResponseEntity<Void> uploadPortfolioVideo(@PathVariable("userId") String userId, @RequestBody MultipartFile video) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/image")
    public ResponseEntity<Void> uploadProfileImage(@RequestBody String url, @RequestBody MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/video")
    public ResponseEntity<Void> uploadProfileVideo(@RequestBody String url, @RequestBody MultipartFile video) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
