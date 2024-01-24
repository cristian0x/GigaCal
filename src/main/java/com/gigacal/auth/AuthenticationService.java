package com.gigacal.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacal.dto.AuthenticateRequestDTO;
import com.gigacal.dto.AuthenticationResponseDTO;
import com.gigacal.dto.RegisterRequestDTO;
import com.gigacal.entity.TokenEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.enums.TokenType;
import com.gigacal.exception.UserAlreadyExistsException;
import com.gigacal.service.impl.JwtServiceImpl;
import com.gigacal.mappers.RegisterRequestToUserMapper;
import com.gigacal.service.impl.SettingService;
import com.gigacal.service.impl.TokenServiceImpl;
import com.gigacal.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final TokenServiceImpl tokenService;
    private final AuthenticationManager authenticationManager;
    private final SettingService settingService;

    public AuthenticationResponseDTO register(final RegisterRequestDTO registerRequestDTO) {
        if (this.userService.getOptionalUserByEmail(registerRequestDTO.getEmail()).isPresent()) {
            LOGGER.warn("User with email={} already exists", registerRequestDTO.getEmail());
            throw new UserAlreadyExistsException("User with email=" + registerRequestDTO.getEmail() + " already exists");
        }

        final UserEntity user = this.userService.saveUser(RegisterRequestToUserMapper.INSTANCE.map(registerRequestDTO));
        this.settingService.createDefaultValueForUser(user);

        LOGGER.info("Successfully created and saved a user={}", user);
        return generateTokensAndReturnAuthenticationResponse(user);
    }

    public AuthenticationResponseDTO authenticate(final AuthenticateRequestDTO authenticateRequestDTO) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequestDTO.getEmail(),
                authenticateRequestDTO.getPassword()));

        final UserEntity user = this.userService.getUserByEmail(authenticateRequestDTO.getEmail());

        return generateTokensAndReturnAuthenticationResponse(user);
    }

    public void refreshToken(final HttpServletRequest request,
                             final HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String refreshToken = authHeader.substring(7);
        final String email = this.jwtService.extractEmail(refreshToken);

        if (nonNull(email)) {
            final UserEntity user = this.userService.getUserByEmail(email);

            if (this.jwtService.isTokenValid(user, refreshToken)) {
                final String token = this.jwtService.generateJwtToken(user);

                this.tokenService.revokeAllUserTokens(user);
                this.saveUserToken(user, token);

                final AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO(token, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponseDTO);
            }
        }
    }

    private AuthenticationResponseDTO generateTokensAndReturnAuthenticationResponse(UserEntity user) {
        final String jwtToken = this.jwtService.generateJwtToken(user);
        final String refreshToken = this.jwtService.generateRefreshToken(user);

        this.saveUserToken(user, jwtToken);
        return new AuthenticationResponseDTO(jwtToken, refreshToken);
    }

    private void saveUserToken(final UserEntity user, final String jwtToken) {
        this.tokenService.saveToken(new TokenEntity(null, jwtToken, TokenType.BEARER, false, false, user));
    }
}
