package com.stefanini.taskmanager.command;

import com.stefanini.taskmanager.command.exceptions.InvalidCommandException;

import static com.stefanini.taskmanager.utils.CommandParameterParser.*;


public class CommandFactory {

    //    -createUser -fn='FirstName' -ln='LastName' -un='UserName'
    public static final String CREATE_USER_COMMAND = "-createUser";
    //    -showAllUsers
    public static final String SHOW_ALL_USERS_COMMAND = "-showAllUsers";
    //    -addTask -un='UserName' -tt='TaskTitle' -td='TaskDescription'
    public static final String ADD_TASK_FOR_COMMAND = "-addTask";
    //    -showTasks -un='UserName'
    public static final String SHOW_TASKS_FOR_COMMAND = "-showTasks";
    //    -deleteTask -un='UserName' -tt='TaskTitle'
    public static final String DELETE_TASK_BY_TITLE_FOR_COMMAND = "-deleteTask";

    public static final int COMMAND = 0;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CommandFactory.class);


    public static Command parseCommandArguments(String[] arguments) throws InvalidCommandException {

        switch (arguments[0]) {
            case CREATE_USER_COMMAND:
                if (arguments.length < 4) {
                    throw new InvalidCommandException("Oops. Please refer to the usage of the command : " + "-createUser -fn='FirstName' -ln='LastName' -un='UserName'");
                }
                return new AddUserCommand(getUsername(arguments),
                        getFirstName(arguments),
                        getLastName(arguments));

            case SHOW_ALL_USERS_COMMAND:
                return new GetAllUsersCommand();

            case ADD_TASK_FOR_COMMAND:
                if (arguments.length < 4) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command : -addTask -un='UserName' -tt='TaskTitle' -td='TaskDescription'");
                }
                return new AddTaskCommand(getTaskTitle(arguments),
                        getTaskDescription(arguments),
                        getUsername(arguments));

            case DELETE_TASK_BY_TITLE_FOR_COMMAND:
                if (arguments.length < 3) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command: -deleteTask -un='UserName' -tt='TaskTitle'");
                }
                return new DeleteTaskCommand(getTaskTitle(arguments), getUsername(arguments));

            case SHOW_TASKS_FOR_COMMAND:
                if (arguments.length < 2) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command : -showTasks -un='UserName'");
                }
                return new GetTasksCommand(getUsername(arguments));
            default:
                throw new InvalidCommandException("Oops. Unknown command [" + arguments[0] + "] Please use one of the following commands: -createUser -showAllUsers -addTask");
        }
    }

}
