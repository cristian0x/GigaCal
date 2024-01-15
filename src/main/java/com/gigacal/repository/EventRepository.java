package com.gigacal.repository;

import com.gigacal.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByUuid(UUID uuid);

    @Query("SELECT e FROM EventEntity e JOIN CalendarEntity c ON e.calendarId = c.id WHERE c.user.id IN :users AND e.removeDate IS NULL")
    List<EventEntity> findAllByUsers(Collection<Long> users);

}
