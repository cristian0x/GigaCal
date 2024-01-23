package com.gigacal.mappers;

import com.gigacal.dto.CalendarDTO;
import com.gigacal.entity.CalendarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CalendarMapper {

    CalendarMapper INSTANCE = Mappers.getMapper(CalendarMapper.class);

    CalendarDTO map(CalendarEntity calendarEntity);

    CalendarEntity map(CalendarDTO calendarDTO);
}
