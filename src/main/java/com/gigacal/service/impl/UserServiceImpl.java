package com.gigacal.service.impl;

import com.gigacal.entity.UserEntity;
import com.gigacal.repository.UserRepository;
import com.gigacal.service.IUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(final UserEntity userEntity) {
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        return this.userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(final String email) {
        return this.getOptionalUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email=" + email + " not found"));
    }

    @Override
    public Optional<UserEntity> getOptionalUserByEmail(final String email) {
        LOGGER.info("Getting a optional user with email={}", email);
        return this.userRepository.getUserEntityByEmail(email);
    }
}
