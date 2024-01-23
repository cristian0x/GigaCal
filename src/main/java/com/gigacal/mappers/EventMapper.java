package com.gigacal.mappers;

import com.gigacal.dto.EventDto;
import com.gigacal.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(imports = {LocalDate.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "start", target = "startStr")
    @Mapping(source = "end", target = "endStr")
    EventEntity map(EventDto eventDto);

    @Mapping(source = "startStr", target = "start")
    @Mapping(source = "endStr", target = "end")
    EventDto map(EventEntity eventEntity);
}
