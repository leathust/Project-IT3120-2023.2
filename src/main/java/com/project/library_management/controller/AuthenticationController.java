package com.project.library_management.controller;

import com.project.library_management.payload.request.auth.LoginRequest;
import com.project.library_management.payload.request.auth.RegisterRequest;
import com.project.library_management.payload.response.BaseResponse;
import com.project.library_management.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(
            @RequestBody RegisterRequest request
    ) {
        var auth = authenticationService.register(request);
        var responseBody = BaseResponse.builder()
                .isError(!auth.isSuccess())
                .message(auth.getMessage())
                .data(auth)
                .build();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Object>> login(
            @RequestBody LoginRequest request
    ) {
        var auth = authenticationService.login(request);
        var responseBody = BaseResponse.builder()
                .isError(!auth.isSuccess())
                .message(auth.getMessage())
                .data(auth)
                .build();
        return ResponseEntity.ok(responseBody);
    }

}
