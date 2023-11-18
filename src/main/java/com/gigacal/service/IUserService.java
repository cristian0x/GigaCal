package com.gigacal.service;

import com.gigacal.entity.UserEntity;

import java.util.Optional;

public interface IUserService {

    UserEntity saveUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);

    Optional<UserEntity> getOptionalUserByEmail(String email);
}
