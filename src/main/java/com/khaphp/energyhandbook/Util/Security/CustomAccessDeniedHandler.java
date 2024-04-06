package com.khaphp.energyhandbook.Util.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ResponseObject responseObject = ResponseObject.builder()
                .code(403)
                .message("(Forbidden) Access denied. You are NOT allow to do this function.")
                .build();
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseObject));
    }
}
