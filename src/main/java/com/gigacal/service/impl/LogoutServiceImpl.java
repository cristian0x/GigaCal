package com.gigacal.service.impl;

import com.gigacal.entity.TokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    private final TokenServiceImpl tokenService;

    @Override
    public void logout(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isNull(authHeader) && !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwt = authHeader.substring(7);
        final TokenEntity token = this.tokenService.findByToken(jwt);

        if (nonNull(token)) {
            token.setExpired(true);
            token.setRevoked(true);

            this.tokenService.saveToken(token);
            SecurityContextHolder.clearContext();
        }
    }
}
