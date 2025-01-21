import java.util.ArrayList;
import java.util.Scanner;

public class Astraea {
    static String separator = "\t____________________________________________________________";
    static ArrayList<Task> list = new ArrayList<>(100);
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        intro();
        String input = scan.nextLine();
        while (!input.equals("bye")) {
            String[] tokens = input.split(" ");
            if (input.equals("list"))
                list();
            else if (tokens[0].equals("mark") && tokens.length == 2 && isNumeric(tokens[1]))
                setDone(Integer.parseInt(tokens[1]));
            else if (tokens[0].equals("unmark") && tokens.length == 2 && isNumeric(tokens[1]))
                setUndone(Integer.parseInt(tokens[1]));
            else
                store(input);
            input = scan.nextLine();
        }
        exit();
    }

    private static void intro() {
        System.out.println(separator);
        System.out.println("\t Astraea here. What do you want?");
        System.out.println(separator);
    }

    private static void echo(String msg) {
        System.out.println(separator);
        System.out.println("\t "+msg);
        System.out.println(separator);
    }

    private static void store(String msg) {
        list.add(new Task(msg));
        System.out.println(separator);
        System.out.println("\t added: "+msg);
        System.out.println(separator);
    }

    private static void list() {
        System.out.println(separator);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t " + (i + 1) + "." + list.get(i));
        }
        System.out.println(separator);
    }

    private static void setDone(int index) {
        list.get(index - 1).setDone();
        System.out.println(separator);
        System.out.println("\t Got that done, have you? Good.");
        System.out.println("\t   " + list.get(index - 1));
        System.out.println(separator);
    }

    private static void setUndone(int index) {
        list.get(index - 1).setUndone();
        System.out.println(separator);
        System.out.println("\t Hm? Better get on that then.");
        System.out.println("\t   " + list.get(index - 1));
        System.out.println(separator);
    }

    private static void exit() {
        System.out.println(separator);
        System.out.println("\t Well. Be on your way, then.");
        System.out.println(separator);
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
