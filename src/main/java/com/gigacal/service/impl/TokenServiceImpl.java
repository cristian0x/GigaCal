package com.gigacal.service.impl;

import com.gigacal.entity.TokenEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.repository.TokenRepository;
import com.gigacal.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TokenServiceImpl implements ITokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(final TokenEntity tokenEntity) {
        this.tokenRepository.save(tokenEntity);
    }

    @Override
    public void revokeAllUserTokens(final UserEntity userEntity) {
        final List<TokenEntity> userTokens = this.tokenRepository.findAllValidTokensByUser(userEntity.getId());

        if (userTokens.isEmpty()) {
            return;
        }

        userTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        this.tokenRepository.saveAll(userTokens);
    }

    @Override
    public TokenEntity findByToken(final String token) {
        return this.tokenRepository.findByToken(token);
    }
}
