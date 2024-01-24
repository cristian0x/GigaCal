package com.gigacal.mappers;

import com.gigacal.dto.SettingDTO;
import com.gigacal.entity.SettingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettingMapper {

    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);

    SettingDTO map(SettingEntity settingEntity);

    SettingEntity map(SettingDTO settingDTO);
}
