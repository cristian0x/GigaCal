package com.gigacal.mappers;

import com.gigacal.dto.EventDTO;
import com.gigacal.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(imports = {LocalDate.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventEntity map(EventDTO eventDto);

    EventDTO map(EventEntity eventEntity);
}
