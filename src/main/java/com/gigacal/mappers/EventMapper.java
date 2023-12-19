package com.gigacal.mappers;

import com.gigacal.dto.EventDto;
import com.gigacal.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(imports = {LocalDate.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventEntity map(EventDto eventDto);

    EventDto map(EventEntity eventEntity);
}
