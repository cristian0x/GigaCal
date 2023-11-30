package com.gigacal.service;

import com.gigacal.entity.TokenEntity;
import com.gigacal.entity.UserEntity;

public interface ITokenService {

    void saveToken(TokenEntity tokenEntity);

    void revokeAllUserTokens(UserEntity userEntity);

    TokenEntity findByToken(String token);
}
