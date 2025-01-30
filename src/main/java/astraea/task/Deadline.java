package astraea.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import astraea.parser.DateParser;

/**
 * Represents a Deadline task.
 * May store the deadline as a LocalDate or LocalDateTime instead of a String, but only if given in the
 * format of yyyy-MM-dd or yyyy-MM-dd HH:mm.
 */
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
        this.deadline = deadlineDate.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
    }

    public Deadline(String name, LocalDateTime deadlineDateTime) {
        super(name);
        this.deadlineDateTime = deadlineDateTime;
        this.deadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    }

    /**
     * Returns the deadline of this Deadline task.
     * May return a formatted String if the deadline is stored as a LocalDate or LocalDateTime.
     * @return Deadline of this task.
     */
    public String getDeadline() {
        if (deadlineDate != null) {
            return deadlineDate.format(DateTimeFormatter.ofPattern("MMMM d, uuuu"));
        } else if (deadlineDateTime != null) {
            return deadlineDateTime.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, uuuu"));
        } else {
            return deadline;
        }
    }

    /**
     * Returns a formatted String to be used for saving this Deadline to file.
     * @return String formatted for saving to file.
     */
    @Override
    public String getSaveStyle() {
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName() + " | " + deadline;
    }

    /**
     * Returns a formatted String to print the state of this Deadline to console.
     * @return String formatted for printing to console.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + this.getDeadline() + ")";
    }

    /**
     * Factory method for creating Deadline objects.
     * Processes the deadline String to store as LocalDate or LocalDateTime if possible.
     * @param name Name of Deadline.
     * @param deadline Deadline time of Deadline.
     * @return Deadline object.
     */
    public static Deadline createDeadline(String name, String deadline) {
        if (DateParser.isLocalDateTime(deadline)) {
            return new Deadline(name,
                    LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")));
        } else if (DateParser.isLocalDate(deadline)) {
            return new Deadline(name,
                    LocalDate.parse(deadline, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
        } else {
            return new Deadline(name, deadline);
        }
    }
}
