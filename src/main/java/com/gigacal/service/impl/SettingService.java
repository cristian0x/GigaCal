package com.gigacal.service.impl;

import com.gigacal.dto.SettingDTO;
import com.gigacal.entity.SettingEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.enums.NotificationChannelType;
import com.gigacal.mappers.SettingMapper;
import com.gigacal.repository.SettingRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SettingService {

    private final Logger LOGGER = LoggerFactory.getLogger(SettingService.class);

    private final SettingRepository settingRepository;
    private final UserServiceImpl userService;

    public void createDefaultValueForUser(final UserEntity userEntity) {
        final SettingEntity settingEntity = new SettingEntity();
        settingEntity.setUserId(userEntity.getId());
        settingEntity.setAreNotificationsEnabled(true);
        settingEntity.setNotificationChannelType(NotificationChannelType.EMAIL);
        settingEntity.setTimeBeforeEvent(15);
        settingEntity.setCreateDate(LocalDateTime.now());
        this.settingRepository.save(settingEntity);
    }

    public SettingDTO editUserSettings(final SettingDTO settingDTO,
                                       final Authentication authentication) {
        final UserEntity loggedUser = this.userService.getUserFromAuthentication(authentication);
        final SettingEntity savedUserSetting = this.settingRepository.findByUserId(loggedUser.getId());
        savedUserSetting.setAreNotificationsEnabled(settingDTO.areNotificationsEnabled());
        savedUserSetting.setUpdateDate(LocalDateTime.now());
        if (Boolean.FALSE.equals(settingDTO.areNotificationsEnabled())) {
            savedUserSetting.setNotificationChannelType(NotificationChannelType.NONE);
            savedUserSetting.setTimeBeforeEvent(0);

            return SettingMapper.INSTANCE.map(this.settingRepository.save(savedUserSetting));
        }
        savedUserSetting.setTimeBeforeEvent(settingDTO.timeBeforeEvent());
        savedUserSetting.setNotificationChannelType(settingDTO.notificationChannelType());

        return SettingMapper.INSTANCE.map(this.settingRepository.save(savedUserSetting));
    }

    public SettingDTO getUserSettings(final Authentication authentication) {
        final UserEntity loggedUser = this.userService.getUserFromAuthentication(authentication);
        final SettingEntity settingEntity = this.settingRepository.findByUserId(loggedUser.getId());
        return SettingMapper.INSTANCE.map(this.settingRepository.save(settingEntity));
    }
}
