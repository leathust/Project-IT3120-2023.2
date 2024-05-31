package com.project.library_management.config.security.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.library_management.constant.MessageConstant;
import com.project.library_management.payload.response.BaseResponse;
import com.project.library_management.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var success = tokenService.revokeToken(jwt);
        if (success) {
            SecurityContextHolder.clearContext();
            response.setContentType("application/json");
            BaseResponse<Object> msg = BaseResponse.builder()
                    .message(MessageConstant.LOGOUT_SUCCESS)
                    .isError(false)
                    .build();
            String json = objectMapper.writeValueAsString(msg);
            response.getWriter().println(json);
        }
    }
}