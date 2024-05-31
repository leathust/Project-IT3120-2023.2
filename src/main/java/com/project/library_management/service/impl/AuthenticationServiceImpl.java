package com.project.library_management.service.impl;

import com.project.library_management.config.security.jwt.JwtService;
import com.project.library_management.config.security.user.CustomUserDetails;
import com.project.library_management.constant.MessageConstant;
import com.project.library_management.model.User;
import com.project.library_management.model.constant.Role;
import com.project.library_management.payload.request.auth.LoginRequest;
import com.project.library_management.payload.request.auth.RegisterRequest;
import com.project.library_management.payload.response.auth.AuthResponse;
import com.project.library_management.service.AuthenticationService;
import com.project.library_management.service.TokenService;
import com.project.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = userService.findByUsername(request.getUsername());
        if (user != null) {
            return AuthResponse.builder()
                    .success(false)
                    .message(MessageConstant.USERNAME_DUPLICATE)
                    .build();
        }
        var tmp_user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .build();
        var saved_user = userService.save(tmp_user);
        CustomUserDetails userDetails = CustomUserDetails.buildFromUser(saved_user);
        var jwtToken = jwtService.generateToken(userDetails);
        tokenService.revokeAllUserToken(saved_user.getId());
        tokenService.saveUserToken(saved_user, jwtToken);
        return AuthResponse.builder()
                .success(true)
                .message(MessageConstant.REGISTER_SUCCESS)
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        var user = userService.findByUsername(request.getUsername());
        if (user != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            CustomUserDetails userDetails = CustomUserDetails.buildFromUser(user);
            var jwtToken = jwtService.generateToken(userDetails);
            tokenService.revokeAllUserToken(user.getId());
            tokenService.saveUserToken(user, jwtToken);
            return AuthResponse.builder()
                    .success(true)
                    .accessToken(jwtToken)
                    .build();
        } else {
            return AuthResponse.builder()
                    .success(false)
                    .message(MessageConstant.USERNAME_PASSWORD_WRONG)
                    .build();
        }
    }
}
