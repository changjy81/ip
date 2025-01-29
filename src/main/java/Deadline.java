import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final String deadline;
    private LocalDate deadlineDate;
    private LocalDateTime deadlineDateTime;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    public Deadline(String name, LocalDate deadlineDate) {
        super(name);
        this.deadlineDate = deadlineDate;
        this.deadline = deadlineDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Deadline(String name, LocalDateTime deadlineDateTime) {
        super(name);
        this.deadlineDateTime = deadlineDateTime;
        this.deadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getDeadline() {
        if (deadlineDate != null) {
            return deadlineDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } else if (deadlineDateTime != null) {
            return deadlineDateTime.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, yyyy"));
        } else {
            return deadline;
        }
    }

    @Override
    public String getSaveStyle() {
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName() + " | " + deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + this.getDeadline() + ")";
    }
}
