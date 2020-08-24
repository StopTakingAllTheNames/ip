package task;

import exception.EmptyTaskException;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public static ToDo create(String task) throws EmptyTaskException {
        if (task.length() <= 5) throw new EmptyTaskException("todo");
        return new ToDo(task.substring(5));
    }

    @Override
    public String print() {
        return "T | "
                + (isDone ? 1 : 0) + " | "
                + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
