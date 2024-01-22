package com.gigacal.repository;

import com.gigacal.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByUuid(UUID uuid);

    List<EventEntity> findAllByCalendarIdIn(List<Long> id);
}
