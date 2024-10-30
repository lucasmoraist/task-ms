package com.lucasmoraist.tasks.domain.entity;

import com.lucasmoraist.tasks.domain.enums.Status;
import com.lucasmoraist.tasks.domain.model.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a task that can be added to a task list.
 *
 * @author lucasmoraist
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_task")
@Table(name = "t_task")
@Builder
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private List<Comment> comment = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
