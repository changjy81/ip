public class AstraeaFileException extends Exception {
    private final String type;

    public AstraeaFileException(String type) {
        this.type = type;
    }

    public String[] getErrorMessage() {
        return switch (type) {
            default -> new String[]{
                "Something went wrong with reading the saved file.",
                "Copying it into backup file and resetting to blank."
            };
        };
    }
}
