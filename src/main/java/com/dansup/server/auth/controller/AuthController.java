package com.dansup.server.auth.controller;

import com.dansup.server.auth.dto.request.SignUpDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@Api(tags = "Auth API", description = "회원가입/로그인 api")
public class AuthController {

    @ApiOperation(value = "Get Kakao Login Auth Code", notes = "카카오톡 로그인 액세스 토큰 추출(프론트에서는 무시하세요)")
    @GetMapping(value = "/signin/kakao/code")
    public ResponseEntity<Void> authKakao(@RequestParam(value = "code", required = false) String code) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Sign Up", notes = "회원 가입")
    @PostMapping(value = "/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpDto signUpDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Sign In", notes = "로그인")
    @PostMapping(value = "/signin")
    public ResponseEntity<Void> signIn() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
