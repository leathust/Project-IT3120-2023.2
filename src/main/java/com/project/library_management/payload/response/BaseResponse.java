package com.project.library_management.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    @JsonProperty("isError")
    private boolean isError;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public static BaseResponse<Object> dataResponse(@NonNull Object obj) {
        return BaseResponse.builder()
                .isError(false)
                .message("")
                .data(obj)
                .build();
    }

    public static BaseResponse<Object> messageResponse(@NonNull String msg) {
        return  BaseResponse.builder()
                .isError(false)
                .message(msg)
                .build();
    }
}
