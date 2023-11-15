package com.gigacal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class AuditEntity {

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime createdBy;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Column(updatable = false)
    private LocalDateTime updatedBy;
}
