package com.gigacal.entity;

import com.gigacal.enums.NotificationChannelType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "settings")
@Data
public class SettingEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Boolean areNotificationsEnabled;

    @Enumerated(EnumType.STRING)
    private NotificationChannelType notificationChannelType;

    private Integer timeBeforeEvent;
}