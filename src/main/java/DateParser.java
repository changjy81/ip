import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static boolean isLocalDate(String str) {
        try {
            LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isLocalDateTime(String str) {
        try {
            LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            // System.out.println("detected datetime");
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
