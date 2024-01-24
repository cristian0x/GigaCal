package com.gigacal.repository;

import com.gigacal.entity.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<SettingEntity, Long> {

    SettingEntity findByUserId(final Long userId);
}
