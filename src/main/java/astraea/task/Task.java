package astraea.task;

/**
 * Represents a task to be stored by Astraea.
 * This class should never be instantiated and serves only as a base for differentiated Task subclasses.
 */
public abstract class Task {
    private final String taskName;
    private boolean isDone = false;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Sets this Task as done.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets this Task as not done.
     */
    public void setUndone() {
        this.isDone = false;
    }

    /**
     * Returns the name of this Task.
     *
     * @return Name of Task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns whether this Task is done.
     *
     * @return Boolean of whether this Task is done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a formatted String representing the done status of this Task.
     *
     * @return String in format of [X] or [ ] based on done status.
     */
    public String printDone() {
        return "[" + (isDone ? "X" : " ") + "]";
    }

    /**
     * Returns a formatted String to be used for saving this Task to file.
     *
     * @return String formatted for saving to file.
     */
    public String getSaveStyle() {
        return "? | " + (isDone ? 1 : 0) + " | " + taskName;
    }

    /**
     * Returns a formatted String to print the state of this Task to console.
     *
     * @return String formatted for printing to console.
     */
    @Override
    public String toString() {
        return printDone() + " " + taskName;
    }
}
