package astraea.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateParserTest {
    @Test
    public void parseDate_test() {
        assertTrue(DateParser.isLocalDate("0000-12-31"));
        assertTrue(DateParser.isLocalDate("9999-01-01"));
        assertFalse(DateParser.isLocalDate("0000-02-32"));
        assertFalse(DateParser.isLocalDate("9999-13-31"));
        assertFalse(DateParser.isLocalDate("40001-12-31"));
    }

    @Test
    public void parseDateTime_test() {
        assertTrue(DateParser.isLocalDateTime("0000-12-31 00:00"));
        assertTrue(DateParser.isLocalDateTime("9999-01-01 23:59"));
        assertFalse(DateParser.isLocalDateTime("0000-02-32 23:59"));
        assertFalse(DateParser.isLocalDateTime("9999-13-31 23:59"));
        assertFalse(DateParser.isLocalDateTime("0000-12-31 24:59"));
        assertFalse(DateParser.isLocalDateTime("9999-13-31 23:60"));
        assertFalse(DateParser.isLocalDateTime("0001-12-31 -3:59"));
    }
}
