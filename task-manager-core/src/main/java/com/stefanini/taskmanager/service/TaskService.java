package com.stefanini.taskmanager.service;

import java.util.List;

import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.service.exceptions.UserNotFoundException;

public interface TaskService {


    /**
     * Finds all tasks
     */
    List<Task> findAllTasks();


    /**
     * Finds tasks for the specified username
     *
     * @param username a <code>String</code> the username given in order to get the tasks for
     */
    List<Task> getTasksFor(String username) throws UserNotFoundException;


    /**
     * Deletes tasks, by title, for the specified username
     *
     * @param taskTitle a <code>String</code> representing the title of the task which will be deleted
     * @throws UserNotFoundException when the user is not found, this exception is thrown
     */
    void deleteTaskByTitle(String taskTitle) throws UserNotFoundException;


    /**
     * Adds tasks to a specific user, by giving his username
     *
     * @param taskTitle       a <code>String</code> representing the title of the task which will be added
     * @param taskDescription a <code>String</code> representing the description of the task which will be added
     * @param username        a <code>String</code> representing the username of the user, for whom the task will be added
     * @throws UserNotFoundException when the user is not found, this exception is thrown
     */
    void addTaskFor(String taskTitle, String taskDescription, String username) throws UserNotFoundException;

    /**
     * Saves a task
     *
     * @param task - a <code>Task</code> which will be saved
     */
    void saveTask(final Task task);


}
