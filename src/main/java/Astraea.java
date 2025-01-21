import java.util.ArrayList;
import java.util.Scanner;

public class Astraea {
    static String separator = "\t____________________________________________________________";
    static ArrayList<String> list = new ArrayList<>(100);
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        intro();
        String input = scan.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) list();
            else store(input);
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

    private static void store(String msg) {
        list.add(msg);
        System.out.println(separator);
        System.out.println("\tadded: "+msg);
        System.out.println(separator);
    }

    private static void list() {
        System.out.println(separator);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.get(i));
        }
        System.out.println(separator);
    }

    private static void exit() {
        System.out.println(separator);
        System.out.println("\tHm. Be on your way, then.");
        System.out.println(separator);
    }
}
