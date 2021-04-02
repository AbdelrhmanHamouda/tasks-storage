package com.pomos.modules;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class TaskSaveCommand {

    @NotBlank
    private String summary;

    private String priority;

    public TaskSaveCommand(String summary) {
        this.summary = summary;
        this.priority = PriorityLevels.LOW.name();
    }
}
