package com.gigacal.repository;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    Optional<CalendarEntity> findByIdAndUserId(Long calendarId, Long userId);

    List<CalendarEntity> findAllByUser(UserEntity userEntity);

    @Query("SELECT calendar FROM CalendarEntity calendar WHERE calendar.name = ?1 AND calendar.user.id = ?2")
    Optional<CalendarEntity> findByCalendarNameAndUserId(String name, Long userId);

    @Query("SELECT calendar FROM CalendarEntity calendar WHERE calendar.user.id = ?1")
    Optional<List<CalendarEntity>> findCalendarsByUserId(final Long userId);

    @Query("SELECT calendar FROM CalendarEntity calendar WHERE calendar.name = ?1")
    Optional<List<CalendarEntity>> findCalendarsByName(final String name);
}
