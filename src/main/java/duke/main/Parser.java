package duke.main;

import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.AddToDoCommand;
import duke.command.ByeCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.HelpCommand;
import duke.command.ListCommand;
import duke.command.TaskListOperator;
import duke.exception.InvalidCommandException;

/**
 * Parser is a class used by Duke to interpret user inputs as commands.
 */
public class Parser {
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a new Parser object.
     *
     * @param storage Storage for saving the TaskList to a file with.
     * @param taskList TaskList to be operated on.
     */
    public Parser(Storage storage, TaskList taskList) {
        this.storage = storage;
        this.taskList = taskList;
    }

    /**
     * Interprets user input and executes commands.
     * The method supports commands for adding, deleting, viewing and completing tasks.
     * If invalid input is given, users are informed and told to try again.
     *
     * @param input User command in console.
     * @return Output message upon execution of command.
     */
    public String parse(String input) {
        if (input.equals("list")) {
            return new ListCommand(taskList).execute();
        } else if (input.equals("bye")) {
            return new ByeCommand(storage, taskList).execute();
        } else if (input.equals("help")) {
            return new HelpCommand().execute();
        } else {
            return operateOnTaskList(input);
        }
    }

    /**
     * Performs operations on the TaskList and returns a message detailing the outcome.
     *
     * @param input User command parsed into Duke.
     * @return Response detailing outcome of operation.
     */
    private String operateOnTaskList(String input) {
        try {
            String keyword = extractCommand(input);
            TaskListOperator taskListOperator;
            switch (keyword) {
            case "done":
                taskListOperator = new DoneCommand(input.substring(5), taskList);
                break;
            case "delete":
                taskListOperator = new DeleteCommand(input.substring(7), taskList);
                break;
            case "find":
                taskListOperator = new FindCommand(input.substring(5), taskList);
                break;
            case "todo":
                taskListOperator = new AddToDoCommand(input.substring(5), taskList);
                break;
            case "event":
                taskListOperator = new AddEventCommand(input.substring(5), taskList);
                break;
            case "deadline":
                taskListOperator = new AddDeadlineCommand(input.substring(5), taskList);
                break;
            default:
                throw new InvalidCommandException();
            }

            return taskListOperator.execute();
        } catch (InvalidCommandException e) {
            return e.toString();
        }
    }

    /**
     * Returns the command word in the user input that is not "list" or "bye".
     * This is the first word in the input line.
     *
     * @param input User input.
     * @return TaskListOperator word.
     * @throws InvalidCommandException If only one word was provided.
     */
    private String extractCommand(String input) throws InvalidCommandException {
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex <= 0) {
            throw new InvalidCommandException();
        }

        return input.substring(0, spaceIndex);
    }
}
