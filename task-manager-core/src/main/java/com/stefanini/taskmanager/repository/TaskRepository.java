package com.stefanini.taskmanager.repository;


import java.util.List;

import com.stefanini.taskmanager.entity.Task;

public interface TaskRepository {


    /**
     * Finds all tasks
     */
    List<Task> findAllTasks();

    /**
     * Deletes tasks
     *
     * @param task - a <code>Task</code> representing the task which will be deleted
     */
    void saveTask(final Task task);


    /**
     * Deletes tasks, by title, for the specified username
     *
     * @param taskTitle - a <code>String</code> representing the title of the task which will be deleted
     * @return
     */
    void deleteTaskByTitle(String taskTitle);

    /**
     * Finds tasks for the specified username
     *
     * @param username - a <code>String</code> representing the username given in order to get the tasks for
     */
    List<Task> getTasksFor(String username);

    /**
     * Adds tasks to a specific user, by giving his username
     *
     * @param task     -a <code>Task</code> representing the task which will be added
     * @param username -a <code>String</code> representing the username of the user, for whom the task will be added
     */
    int saveTaskFor(Task task, String username);
}
