package com.pomos.modules;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateCommand {
    @NotNull
    private Long id;

    private String summary;
    private PriorityLevels priority;

    public TaskUpdateCommand(String summary) {
        this.summary = summary;
    }

    public TaskUpdateCommand(PriorityLevels priority) {
        this.priority = priority;
    }
}
