package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.InvalidDateException;
import duke.exception.MissingDateException;

/**
 * Class representing a deadline task.
 * A deadline task has a description containing the details of the task and
 * a date by which the task is due.
 */
public class Deadline extends Task {
    private final LocalDate dueDate;

    private Deadline(String description, LocalDate dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    /**
     * Creates a new Deadline object represented by the given command.
     * The command is split into two parts by the " /by " delimiter, with the
     * first being the task description and the second being the due date.
     *
     * @param task Description of task to be converted to a Deadline.
     * @return Deadline object representing the given description.
     * @throws MissingDateException If " /by " is not given or if there is no date provided.
     * @throws InvalidDateException If the date provided is in the wrong format.
     */
    public static Deadline create(String task)
            throws MissingDateException, InvalidDateException {
        String[] taskInfo = task.split(" /by ", 2);
        if (taskInfo.length < 2) {
            throw new MissingDateException();
        }

        LocalDate dateTime = null;
        for (DateFormat format : DateFormat.values()) {
            try {
                dateTime = LocalDate.parse(taskInfo[1], format.toDateFormat());
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }
        if (dateTime == null) {
            throw new InvalidDateException();
        }
        return new Deadline(taskInfo[0], dateTime);
    }

    /**
     * Creates a new Deadline object represented by its String when read from a file.
     *
     * @param task Description of task.
     * @param date Date by which the task is to be completed by.
     * @return Deadline object representing the given details.
     */
    public static Deadline createFromFile(String task, String date) {
        DateTimeFormatter format = DateFormat.FORMAT6.toDateFormat();
        return new Deadline(task, LocalDate.parse(date, format));
    }

    @Override
    public String toDataString() {
        return "D | "
                + (isDone ? 1 : 0) + " | "
                + this.description + " | "
                + this.dueDate;
    }

    @Override
    public String toString() {
        String dateTime = dueDate.format(DateFormat.FORMAT5.toDateFormat());
        return "[D]" + super.toString() + " (by: " + dateTime + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Deadline) {
            return description.equals(((Deadline) obj).description)
                    && dueDate.equals(((Deadline) obj).dueDate);
        } else {
            return false;
        }
    }
}
