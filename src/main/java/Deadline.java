public class Deadline extends Task {
    private final String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public String getSaveStyle() {
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName() + " | " + this.getDeadline();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + deadline + ")";
    }
}
