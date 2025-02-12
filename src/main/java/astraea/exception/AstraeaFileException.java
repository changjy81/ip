package astraea.exception;

/**
 * Represents an Exception relating to file read/write in Astraea.
 * Currently not used.
 */
public class AstraeaFileException extends Exception {
    private final String type;

    public AstraeaFileException(String type) {
        this.type = type;
    }

    /**
     * Returns the error message to be printed to UI associated with this exception.
     *
     * @return String array of error messages.
     */
    public String[] getErrorMessage() {
        return switch (type) {
        default -> new String[]{
            "Something went wrong with reading the saved file.",
            "Copying it into backup file and resetting to blank."
        };
        };
    }
}
