package com.pomos.interfaces;

import com.pomos.tables.Task;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(@NotNull Long id);

    Task save(@NotNull String summary, String priority);

    Task save(@NotNull String summary);

    Long deleteTask(@NotNull Long id);

    int completeTask(@NotNull Long id);

    int updatePriority(@NotNull Long id, @NotNull String priority);

    int updateSummary(@NotNull Long id, @NotNull String summary);

    List<Task> listAllTasks();
}
