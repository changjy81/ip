package astraea.task;

/**
 * Represents a basic todo Task.
 */
public class Todo extends Task {
    public Todo(String taskName) {
        super(taskName);
    }

    /**
     * Returns a formatted String to be used for saving this Todo to file.
     * @return String formatted for saving to file.
     */
    @Override
    public String getSaveStyle() {
        return "T | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName();
    }

    /**
     * Returns a formatted String to print the state of this Todo to console.
     * @return String formatted for printing to console.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
