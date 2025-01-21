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

    @Override
    public String toString() {
        return "[" + (done ? "X" : " ") + "] " + taskName;
    }
}
