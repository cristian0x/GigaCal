package com.gigacal.controller;

import com.gigacal.dto.SettingDTO;
import com.gigacal.service.impl.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class SettingsController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<SettingDTO> getUserSettings(final Authentication authentication) {
        return ResponseEntity.ok(this.settingService.getUserSettings(authentication));
    }

    @PutMapping
    public ResponseEntity<SettingDTO> editUserSettings(@RequestBody final SettingDTO settingDTO,
                                                       final Authentication authentication) {
        return ResponseEntity.ok(this.settingService.editUserSettings(settingDTO, authentication));
    }
}
