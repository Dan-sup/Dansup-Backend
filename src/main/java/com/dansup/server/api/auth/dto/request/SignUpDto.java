package com.dansup.server.api.auth.dto.request;

import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ApiModel
public class SignUpDto {

    @ApiModelProperty(value = "프로필 이미지", example = "이미지 multipartfile")
    private MultipartFile profileImage;

    @ApiModelProperty(value = "계정 정보", example = "@younaring__")
    private String username;

    @ApiParam(value = "댄서명", example = "유나")
    private String nickname;

    @ApiParam(value = "프로필 대표 영상", example = "이미지 multipartfile")
    private MultipartFile profileVideo;

    @ApiParam(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiParam(value = "댄스 장르 리스트")
    private List<GenreRequestDto> genres;

    @ApiParam(value = "해시 태그 리스트")
    private List<HashtagRequestDto> hashtags;

    @ApiParam(value = "경력 리스트")
    private List<GetPortfolioDto> portfolios;

}
