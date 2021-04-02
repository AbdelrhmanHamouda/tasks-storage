package com.pomos.controllers;

import com.pomos.interfaces.TaskRepository;
import com.pomos.modules.TaskSaveCommand;
import com.pomos.modules.TaskUpdateCommand;
import com.pomos.tables.Task;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import java.util.List;

import static com.pomos.utils.UriUtils.location;

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
                .created(savedTask)
                .headers(headers -> headers.location(location(savedTask.getId())));
    }

    @Put("/update")
    public HttpResponse<String> updateTask(@Body @Valid TaskUpdateCommand taskUpdateCommand) {
        try {
            if (!taskUpdateCommand.getSummary().isBlank() && !taskUpdateCommand.getSummary().isEmpty()) {
                taskRepository.updateSummary(taskUpdateCommand.getId(), taskUpdateCommand.getSummary());
            }
        } catch (NullPointerException ignored) {
            // Supported behaviour, Do nothing
        }

        try {
            String taskPriority = taskUpdateCommand.getPriority().toString();
            if (!taskPriority.isBlank() && !taskPriority.isEmpty()) {
                taskRepository.updatePriority(taskUpdateCommand.getId(), taskUpdateCommand.getPriority().name());
            }
        } catch (NullPointerException ignored) {
            // Supported behaviour, Do nothing
        }

        return HttpResponse
                .accepted()
                .body("Task updated")
                .header(HttpHeaders.LOCATION, location(taskUpdateCommand.getId()).getPath());
    }

    @Get("/list")
    public List<Task> listAllTasks() {
        return taskRepository.listAllTasks();
    }

    @Delete("/{id}")
    public HttpResponse deleteTask(Long id) {
        taskRepository.deleteTask(id);
        return HttpResponse.ok();
    }
}
