package task;

import exception.EmptyTaskException;
import exception.InvalidDateException;
import exception.MissingDateException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDate date;

    private Event(String description, LocalDate date) {
        super(description);
        this.date = date;
    }

    public static Event create(String task)
            throws EmptyTaskException, MissingDateException, InvalidDateException {
        if (task.length() <= 6) throw new EmptyTaskException("event");
        String[] taskInfo = task.substring(6).split(" /at ", 2);
        if (taskInfo.length < 2) throw new MissingDateException();
        LocalDate dateTime = null;
        for (DateTimeFormat format : DateTimeFormat.values()) {
            try {
                dateTime = LocalDate.parse(taskInfo[1], format.toDateFormat());
            } catch (DateTimeParseException e) { }
        }
        if (dateTime == null) throw new InvalidDateException();
        return new Event(taskInfo[0], dateTime);
    }

    @Override
    public String toString() {
        String dateTime = date.format(DateTimeFormat.FORMAT5.toDateFormat());
        return "[E]" + super.toString() + " (at: " + dateTime + ")";
    }
}
