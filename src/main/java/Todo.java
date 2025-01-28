public class Todo extends Task {
    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String getSaveStyle() {
        return "T | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
