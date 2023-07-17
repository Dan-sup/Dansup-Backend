package com.dansup.server.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

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

    public static <T> Response<T> success(ResponseCode responseCode) {
        return Response.<T>builder()
                .isSuccess(true)
                .code(responseCode.getHttpStatus().value())
                .message("요청에 성공하였습니다.")
                .data(null)
                .build();
    }

    public static <T> Response<T> success(ResponseCode responseCode, T data) {
        return Response.<T>builder()
                .isSuccess(true)
                .code(responseCode.getHttpStatus().value())
                .message("요청에 성공하였습니다.")
                .data(data)
                .build();
    }

    public static <T> Response<T> fail(ResponseCode exceptionCode) {
        return Response.<T>builder()
                .isSuccess(false)
                .code(exceptionCode.getHttpStatus().value())
                .message(exceptionCode.getMessage())
                .data(null)
                .build();
    }

    public static JSONObject setJsonExceptionResponse(ResponseCode exception) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isSuccess", false);
        jsonObject.put("code", exception.getHttpStatus().value());
        jsonObject.put("message", exception.getMessage());
        return jsonObject;
    }

}
