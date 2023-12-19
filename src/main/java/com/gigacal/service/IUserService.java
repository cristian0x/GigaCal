package com.gigacal.service;

import com.gigacal.entity.UserEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IUserService {

    UserEntity saveUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);

    Optional<UserEntity> getOptionalUserByEmail(String email);

    UserEntity getUserFromAuthentication(Authentication authentication);
}
