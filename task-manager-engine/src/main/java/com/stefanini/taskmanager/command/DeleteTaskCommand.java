package com.stefanini.taskmanager.command;

import com.stefanini.taskmanager.command.exceptions.InvalidCommandException;
import com.stefanini.taskmanager.service.ServiceFactory;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.exceptions.UserNotFoundException;

public class DeleteTaskCommand implements Command, Runnable {
    private String taskTitle;
    private String username;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeleteTaskCommand.class);

    public DeleteTaskCommand(String taskTitle, String username) {
        this.taskTitle = taskTitle;
        this.username = username;
    }


    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();


    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {
        taskService.deleteTaskByTitle(taskTitle);
        log.info("Task [" + taskTitle + "] deleted successfully");
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InvalidCommandException | UserNotFoundException e) {
            System.out.println("Something bad happened during deleting task : " + e.getMessage());
            log.trace(e.getMessage(), e);
        }
    }
}
