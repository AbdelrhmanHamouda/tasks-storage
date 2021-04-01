package com.pomos.interfaces;

import com.pomos.tables.Task;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(@NotNull Long id);

    Task save(@NotNull String summary, @NotNull LocalDateTime dateCreated, String priority);

    Task save(@NotNull String summary, @NotNull LocalDateTime dateCreated);

    Long deleteTask(@NotNull Long id);

    Task completeTask(@NotNull Long id);

    Task updatePriority(@NotNull Long id, @NotNull String priority);

    Task updateSummary(@NotNull Long id, @NotNull String summary);

    List<Task> listAllTasks();
}
