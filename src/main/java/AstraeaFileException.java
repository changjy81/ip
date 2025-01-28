public class AstraeaFileException extends Exception {
    private final String type;
    private static String separator = "\t____________________________________________________________";

    public AstraeaFileException(String type) {
        this.type = type;
    }

    public void print() {
        System.out.println(separator);
        switch (type) {
        default:
            System.out.println("\t Something went wrong with reading the saved file.");
            System.out.println("\t Copying it into backup file and resetting to blank.");
        }
        System.out.println(separator);
    }
}
