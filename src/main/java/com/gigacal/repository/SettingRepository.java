package com.gigacal.repository;

import com.gigacal.entity.SettingEntity;
import com.gigacal.enums.SettingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<SettingEntity, Long> {

    List<SettingEntity> findAllByType(SettingType type);

    Optional<SettingEntity> findByUserIdAndType(Long userId, SettingType type);

}
