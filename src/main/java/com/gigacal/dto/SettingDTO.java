package com.gigacal.dto;

import com.gigacal.enums.NotificationChannelType;

public record SettingDTO(
        Boolean areNotificationsEnabled,
        NotificationChannelType notificationChannelType,
        Integer timeBeforeEvent
) {}
