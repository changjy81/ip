public class Task {
    private final String taskName;
    private boolean done = false;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public void setDone() {
        this.done = true;
    }

    public void setUndone() {
        this.done = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isDone() {
        return done;
    }

    public String printDone() {
        return "[" + (done ? "X" : " ") + "]";
    }

    public String getSaveStyle() {
        return "? | " + (done ? 1 : 0) + " | " + taskName;
    }

    @Override
    public String toString() {
        return printDone() + " " + taskName;
    }
}
