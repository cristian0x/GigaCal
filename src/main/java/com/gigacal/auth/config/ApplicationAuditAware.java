package com.gigacal.auth.config;

import com.gigacal.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static java.util.Objects.isNull;

public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isNull(authentication) || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        final UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return Optional.ofNullable(userEntity.getId());
    }
}
