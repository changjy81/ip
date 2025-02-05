package astraea.ui;

import java.util.Scanner;

/**
 * UI interaction class for intro, exit and printing to console.
 */
public class AstraeaUi {
    private static final String SEPARATOR = "\t____________________________________________________________";
    private final Scanner scan = new Scanner(System.in);

    public String readInput() {
        return scan.nextLine();
    }

    /**
     * Returns the intro statement.
     */
    public String[] intro() {
        printBoundedMessage("Astraea here. What do you want?");
        return new String[]{"Astraea here. What do you want?"};
    }

    /**
     * Returns the exit statement.
     */
    public String[] exit() {
        printBoundedMessage("Well. Be on your way, then.");
        return new String[]{"Well. Be on your way, then."};
    }

    /**
     * Prints the given message surrounded by separator lines.
     *
     * @param message Message to be printed.
     */
    public void printBoundedMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println("\t " + message);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the given messages in the array line by line, with separator lines at the top and bottom of the block.
     *
     * @param message Messages to be printed, split by line.
     */
    public void printBoundedMessage(String[] message) {
        System.out.println(SEPARATOR);
        for (String s : message) {
            System.out.println("\t " + s);
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the given message with a separator line afterward.
     * @param message Message to be printed.
     */
    public void printBottomBoundedMessage(String message) {
        System.out.println("\t " + message);
        System.out.println(SEPARATOR);
    }

}
