import java.util.Scanner;

public class Astraea {
    static String separator = "\t____________________________________________________________";
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        intro();
        String input = scan.nextLine();
        while (!input.equals("bye")) {
            echo(input);
            input = scan.nextLine();
        }
        exit();
    }

    private static void intro() {
        System.out.println(separator);
        System.out.println("\tAstraea here. What do you want?");
        System.out.println(separator);
    }

    private static void echo(String msg) {
        System.out.println(separator);
        System.out.println("\t"+msg);
        System.out.println(separator);
    }

    private static void exit() {
        System.out.println(separator);
        System.out.println("\tHm. Be on your way, then.");
        System.out.println(separator);
    }
}
