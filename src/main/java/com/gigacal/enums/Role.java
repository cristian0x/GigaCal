package com.gigacal.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("user");

    @Getter
    private final String permission;
}
