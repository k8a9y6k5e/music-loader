package com.krev.musicloader.api.orchestrator;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orchestrator")
@EntityListeners(AuditingEntityListener.class)
public class OrchestratorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority", nullable = false, unique = true)
    private Integer priority;

    @Column(name = "activity", nullable = false)
    private Boolean activity = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
