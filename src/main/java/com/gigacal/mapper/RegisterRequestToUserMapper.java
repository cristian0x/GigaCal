package com.gigacal.mapper;

import com.gigacal.dto.RegisterRequestDTO;
import com.gigacal.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public interface RegisterRequestToUserMapper {

    RegisterRequestToUserMapper INSTANCE = Mappers.getMapper(RegisterRequestToUserMapper.class);

    @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
    UserEntity map(RegisterRequestDTO registerRequestDTO);
}
