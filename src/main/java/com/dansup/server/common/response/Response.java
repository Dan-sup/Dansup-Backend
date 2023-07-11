package com.dansup.server.common.response;

import com.dansup.server.common.exception.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Response<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Response<T> success(int code) {
        return Response.<T>builder()
                .isSuccess(true)
                .code(200)
                .message("요청에 성공하였습니다.")
                .data(null)
                .build();
    }

    public Response<T> success(int code, T data) {
        return Response.<T>builder()
                .isSuccess(true)
                .code(200)
                .message("요청에 성공하였습니다.")
                .data(data)
                .build();
    }

    public Response<T> fail(ExceptionCode exceptionCode) {
        return Response.<T>builder()
                .isSuccess(false)
                .code(exceptionCode.getHttpStatus().value())
                .message(exceptionCode.getMessage())
                .data(null)
                .build();
    }

}
