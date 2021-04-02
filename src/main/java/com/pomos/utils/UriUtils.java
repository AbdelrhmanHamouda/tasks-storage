package com.pomos.utils;

import com.pomos.tables.Task;

import java.net.URI;

public class UriUtils {

    /**
     * Method to create a new path for any newly created task
     *
     * @param id of the task to create the URI for.
     * @return created URI location
     */
    public static URI location(Long id) {
        return URI.create("/tasks/" + id);
    }

    public static URI location(Task task) {
        return location(task.getId());
    }
}
