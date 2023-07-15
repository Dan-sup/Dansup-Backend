package com.dansup.server.api.auth.dto.request;

import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @ApiModelProperty(value = "프로필 이미지", example = "이미지 multipartfile")
    private MultipartFile profileImage;

    @ApiModelProperty(value = "계정 정보", example = "@younaring__")
    private String username;

    @ApiModelProperty(value = "댄서명", example = "유나")
    private String nickname;

    @ApiModelProperty(value = "프로필 대표 영상", example = "이미지 multipartfile")
    private MultipartFile profileVideo;

    @ApiModelProperty(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiModelProperty(value = "댄스 장르 리스트")
    @Builder.Default
    private List<GenreRequestDto> genres = new ArrayList<>();

    @ApiModelProperty(value = "해시 태그 리스트", notes = "해시태그 3개를 모두 주셔야해요. 없으면 null로 주세요!")
    @Builder.Default
    private List<HashtagRequestDto> hashtags = new ArrayList<>();

    @ApiModelProperty(value = "경력 리스트")
    @Builder.Default
    private List<GetPortfolioDto> portfolios = new ArrayList<>();

}