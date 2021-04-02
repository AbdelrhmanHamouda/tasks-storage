package com.pomos.controllers;

import com.pomos.interfaces.TaskRepository;
import com.pomos.modules.TaskSaveCommand;
import com.pomos.modules.TaskUpdatePriorityCommand;
import com.pomos.modules.TaskUpdateSummaryCommand;
import com.pomos.tables.Task;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;

@ExecuteOn(TaskExecutors.IO)
@Controller("/tasks")
public class TaskController {

    protected final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Get("/{id}")
    public Task task(Long id) {
        return taskRepository
                .findById(id)
                .orElse(null);
    }

    @Post
    public HttpResponse<Task> save(@Body @Valid TaskSaveCommand command) {
        Task savedTask = taskRepository.save(command.getSummary(), command.getPriority().name());
        return HttpResponse
                .created(savedTask);
    }

    // TODO: Merge both updates into one based on the ID
    @Put("/update/summary")
    public HttpResponse updateSummary(@Body @Valid TaskUpdateSummaryCommand updateSummaryCommand) {
        int tasksUpdatedCount = taskRepository
                .updateSummary(updateSummaryCommand.getId(), updateSummaryCommand.getSummary());
        return HttpResponse
                .accepted()
                .body("{'Updated tasks' :" + tasksUpdatedCount + "}");
    }

    @Put("/update/priority")
    public HttpResponse updatePriority(@Body @Valid TaskUpdatePriorityCommand taskUpdatePriorityCommand) {
        int tasksUpdatedCount = taskRepository
                .updateSummary(taskUpdatePriorityCommand.getId(), taskUpdatePriorityCommand.getPriority());
        return HttpResponse
                .accepted()
                .body("{'Updated tasks' :" + tasksUpdatedCount + "}");
    }

    // TODO: create list all method
}
