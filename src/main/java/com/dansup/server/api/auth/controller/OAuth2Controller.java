package com.dansup.server.api.auth.controller;

import com.dansup.server.api.auth.service.OAuth2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oauth")
@Api(tags = "OAuth2.0 API", description = "카카오톡 OAuth2.0 api(프론트는 신경X)")
@Slf4j
public class OAuth2Controller {

    private final OAuth2Service authService;

    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=91cfc73a730663e93196247d884f837e&redirect_uri=http://localhost:8080/auth/signin/kakao

    @ApiOperation(value = "Get Kakao Login Auth Code", notes = "카카오톡 로그인 액세스 토큰 추출(프론트에서는 무시하세요)")
    @GetMapping(value = "/sign-in/kakao")
    public ResponseEntity<Void> authKakao(@RequestParam(value = "code", required = false) String code) throws ParseException {
        log.info("code: " + code);
        authService.getKakaoAccessToken(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
