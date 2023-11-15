package com.gigacal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class AuditEntity {

    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

    @Column(updatable = false)
    private LocalDateTime removeDate;

}
