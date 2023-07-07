package com.dansup.server.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

@ApiModel
public class UploadFileDto {

    @ApiModelProperty(value = "업로드할 파일", example = "multipartfile")
    private MultipartFile file;

}
