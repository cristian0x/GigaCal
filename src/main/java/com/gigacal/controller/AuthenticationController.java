package com.gigacal.controller;

import com.gigacal.dto.AuthenticateRequestDTO;
import com.gigacal.dto.AuthenticationResponseDTO;
import com.gigacal.auth.AuthenticationService;
import com.gigacal.dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Valid final RegisterRequestDTO registerRequestDTO) {
        LOGGER.info("Starting register process for RegisterRequestDTO={}", registerRequestDTO);
        final AuthenticationResponseDTO authenticationResponseDTO = this.authenticationService.register(registerRequestDTO);

        LOGGER.info("Register successful - returning AuthenticationResponse={}", authenticationResponseDTO);
        return ResponseEntity.ok(authenticationResponseDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid final AuthenticateRequestDTO authenticateRequestDTO) {
        LOGGER.info("Starting authentication process for AuthenticateRequestDTO={}", authenticateRequestDTO);
        final AuthenticationResponseDTO authenticationResponseDTO = this.authenticationService.authenticate(authenticateRequestDTO);

        return ResponseEntity.ok(authenticationResponseDTO);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(final HttpServletRequest request,
                             final HttpServletResponse response) throws IOException {
        this.authenticationService.refreshToken(request, response);
    }
}
