package com.pomos.tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "summary", nullable = false)
    private  String summary;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_completed", nullable = true)
    private LocalDateTime dateCompleted;

    @Column(name = "priority", nullable = true)
    private  String priority;

    public Task(@NotNull String summary, @NotNull LocalDateTime dateCreated, String priority) {
        this.summary = summary;
        this.dateCreated = dateCreated;
        this.priority = priority;
    }
}
