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

    @ApiModelProperty(value = "계정 정보", example = "@younaring__")
    private String username;

    @ApiModelProperty(value = "댄서명", example = "유나")
    private String nickname;

    @ApiModelProperty(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiModelProperty(value = "댄스 장르 리스트")
    @Builder.Default
    private List<GenreRequestDto> genres = new ArrayList<>();

    @ApiModelProperty(value = "해시 태그1", example = "#하이하이")
    private String hashtag1;

    @ApiModelProperty(value = "해시 태그2", example = "#브이브이")
    private String hashtag2;

    @ApiModelProperty(value = "해시 태그3", example = "#바이바이")
    private String hashtag3;

    @ApiModelProperty(value = "경력 리스트")
    @Builder.Default
    private List<GetPortfolioDto> portfolios = new ArrayList<>();

}