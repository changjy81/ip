import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final String startTime;
    private LocalDateTime startDateTime;
    private LocalDate startDate;
    private final String endTime;
    private LocalDateTime endDateTime;
    private LocalDate endDate;

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String name, LocalDate startDate, LocalDate endDate) {
        super(name);
        this.startDate = startDate;
        this.startTime = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDate = endDate;
        this.endTime = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Event(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(name);
        this.startDateTime = startDateTime;
        this.startTime = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.endDateTime = endDateTime;
        this.endTime = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getStartTime() {
        if (startDateTime != null) {
            return startDateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } else if (startDate != null) {
            return startDate.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, yyyy"));
        } else {
            return startTime;
        }
    }

    public String getEndTime() {
        if (endDateTime != null) {
            return endDateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } else if (endDate != null) {
            return endDate.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, yyyy"));
        } else {
            return endTime;
        }
    }

    @Override
    public String getSaveStyle() {
        return "E | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName()
                + " | " + this.startTime + " | " + this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.getStartTime() + " to: " + this.getEndTime() + ")";
    }
}
