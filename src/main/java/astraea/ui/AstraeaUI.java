package astraea.ui;

import java.util.Scanner;

public class AstraeaUI {
    static String separator = "\t____________________________________________________________";
    private final Scanner scan = new Scanner(System.in);

    public String readInput() {
        return scan.nextLine();
    }

    public void intro() {
        printBoundedMessage("astraea.Astraea here. What do you want?");
    }

    public void exit() {
        printBoundedMessage("Well. Be on your way, then.");
    }

    public void printBoundedMessage(String message) {
        System.out.println(separator);
        System.out.println("\t " + message);
        System.out.println(separator);
    }

    public void printBoundedMessage(String[] message) {
        System.out.println(separator);
        for (String s : message) {
            System.out.println("\t " + s);
        }
        System.out.println(separator);
    }

    public void printBottomBoundedMessage(String message) {
        System.out.println("\t " + message);
        System.out.println(separator);
    }

}
