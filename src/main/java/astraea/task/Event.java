package astraea.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import astraea.parser.DateParser;

/**
 * Represents an Event task.
 * May store the start and end time as a LocalDate or LocalDateTime instead of a String, but only if both
 * are given in the format of yyyy-MM-dd or yyyy-MM-dd HH:mm.
 */
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

    /**
     * Returns the start time of this Event.
     * May return a formatted String if the deadline is stored as a LocalDate or LocalDateTime.
     * @return Start time of this task.
     */
    public String getStartTime() {
        if (startDateTime != null) {
            return startDateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } else if (startDate != null) {
            return startDate.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, yyyy"));
        } else {
            return startTime;
        }
    }

    /**
     * Returns the end time of this Event.
     * May return a formatted String if the deadline is stored as a LocalDate or LocalDateTime.
     * @return End time of this task.
     */
    public String getEndTime() {
        if (endDateTime != null) {
            return endDateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } else if (endDate != null) {
            return endDate.format(DateTimeFormatter.ofPattern("HH:mm, MMMM d, yyyy"));
        } else {
            return endTime;
        }
    }

    /**
     * Returns a formatted String to be used for saving this Event to file.
     * @return String formatted for saving to file.
     */
    @Override
    public String getSaveStyle() {
        return "E | " + (this.isDone() ? 1 : 0) + " | " + this.getTaskName()
                + " | " + this.startTime + " | " + this.endTime;
    }

    /**
     * Returns a formatted String to print the state of this Event to console.
     * @return String formatted for printing to console.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.getStartTime() + " to: " + this.getEndTime() + ")";
    }

    /**
     * Factory method for creating Event objects.
     * Processes the start and end Strings to store as LocalDate or LocalDateTime if possible.
     * Both Strings must be in the specified formats to be stored as LocalDate or LocalDateTime.
     * @param name Name of Event.
     * @param start Start time of Event.
     * @param end End time of Event.
     * @return Event object.
     */
    public static Event createEvent(String name, String start, String end) {
        if (DateParser.isLocalDateTime(start) && DateParser.isLocalDateTime(end)) {
            LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new Event(name, startTime, endTime);
        } else if (DateParser.isLocalDate(start) && DateParser.isLocalDate(end)) {
            LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return new Event(name, startDate, endDate);
        } else {
            return new Event(name, start, end);
        }
    }
}
