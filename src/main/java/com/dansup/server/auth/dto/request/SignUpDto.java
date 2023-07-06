package com.dansup.server.auth.dto.request;

import com.dansup.server.profile.dto.response.GetPortfolioDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SignUpDto {

    private MultipartFile profileImage;

    private String username;

    private String nickname;

    private MultipartFile profileVideo;

    private String intro;

    private List<GenreRequestDto> genres;

    private List<HashtagRequestDto> hashtags;

    private List<GetPortfolioDto> portfolios;

}
