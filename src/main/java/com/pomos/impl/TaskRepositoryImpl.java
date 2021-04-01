package com.pomos.impl;

import com.pomos.interfaces.TaskRepository;
import com.pomos.tables.Task;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class TaskRepositoryImpl implements TaskRepository {

    public static final String DEFAULT_PRIORITY = "low";
    private final EntityManager entityManager;

    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to find a task by its db ID.
     *
     * @param id ID to match in the db.
     * @return Matched Task if found.
     */
    @Override
    @ReadOnly
    public Optional<Task> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    /**
     * Method to save a new task.
     *
     * @param summary  Task summary
     * @param priority Task priority
     * @return created task.
     */
    @Override
    @Transactional
    public Task save(@NotNull String summary, String priority) {
        LocalDateTime dateCreated = LocalDateTime.now();

        Task task = new Task(summary, dateCreated, priority);

        entityManager.persist(task);
        return task;
    }

    /**
     * Method to save a new task with default "low" priority
     *
     * @param summary Task summary
     * @return created task.
     */
    @Override
    @Transactional
    public Task save(@NotNull String summary) {
        LocalDateTime dateCreated = LocalDateTime.now();

        Task task = new Task(summary, dateCreated, DEFAULT_PRIORITY);

        entityManager.persist(task);
        return task;
    }

    /**
     * Method to remove a task based on the provided id.
     *
     * @param id Id of the task to remove.
     * @return id of removed task.
     */
    @Override
    @Transactional
    public Long deleteTask(@NotNull Long id) {
        findById(id).ifPresent(entityManager::remove);
        return id;
    }

    /**
     * Method to update the complete date of a given task
     *
     * @param id id of the task to update the date for.
     * @return number of tasks updated.
     */
    @Override
    @Transactional
    public int completeTask(@NotNull Long id) {
        LocalDateTime dateCompleted = LocalDateTime.now();
        return entityManager.createQuery("UPDATE Task t SET date_completed = :dateCompleted where id = :id")
                .setParameter("dateCompleted", dateCompleted)
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Method to update the priority of a given task
     *
     * @param id id of the task to update the priority for.
     * @return number of tasks updated.
     */
    @Override
    @Transactional
    public int updatePriority(@NotNull Long id, @NotNull String priority) {
        return entityManager.createQuery("UPDATE Task t SET priority = :priority where id = :id")
                .setParameter("priority", priority)
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Method to update the summary of a given task
     *
     * @param id id of the task to update the summary for.
     * @return number of tasks updated.
     */
    @Override
    @Transactional
    public int updateSummary(@NotNull Long id, @NotNull String summary) {
        return entityManager.createQuery("UPDATE Task t SET summary = :summary where id = :id")
                .setParameter("summary", summary)
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Method to return all stored tasks.
     *
     * @return all stored tasks in the db.
     */
    @Override
    @ReadOnly
    public List<Task> listAllTasks() {
        String qlQuery = "SELECT * FROM Task";
        TypedQuery<Task> query = entityManager.createQuery(qlQuery, Task.class);

        return query.getResultList();
    }
}
