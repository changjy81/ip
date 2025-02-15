package astraea.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class containing static methods associated with parsing Strings as LocalDate or LocalDateTime.
 */
public class DateParser {
    /**
     * Checks if a String is in a suitable format for processing by attempting to parse given String as a
     * LocalDate in a specific format.
     *
     * @param str String representing a date.
     * @return Boolean value of whether the String can be parsed.
     */
    public static boolean isLocalDate(String str) {
        try {
            LocalDate.parse(str, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if a String is in a suitable format for processing by attempting to parse given String as a
     * LocalDateTime in a specific format.
     *
     * @param str String representing a datetime.
     * @return Boolean value of whether the String can be parsed.
     */
    public static boolean isLocalDateTime(String str) {
        try {
            LocalDateTime.parse(str, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
