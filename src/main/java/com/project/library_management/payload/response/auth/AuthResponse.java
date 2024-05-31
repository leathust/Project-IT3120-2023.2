package com.project.library_management.payload.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class AuthResponse {

    @JsonIgnore
    private boolean success;

    @JsonIgnore
    private String message;

    @JsonProperty("accessToken")
    private String accessToken;

}
