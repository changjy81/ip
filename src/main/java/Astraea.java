import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Astraea {
    static String separator = "\t____________________________________________________________";
    static ArrayList<Task> list = new ArrayList<>(100);
    static Scanner scan = new Scanner(System.in);
    static final String savePath = "data/tasks.txt";

    public static void main(String[] args) {
        intro();

        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File(savePath);
            if (file.createNewFile()) {
                // no task save data found, created new file
                System.out.println("\t I have no data recorded. New storage file created.");
                System.out.println(separator);
            } else {
                // read existing save data
                read();
                System.out.println("\t I've retrieved your tasks from last time.");
                // System.out.println(file.getAbsoluteFile());
                System.out.println(separator);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (AstraeaFileException ae) {
            // invalid/corrupted data
            // TODO copy bad data into new file, reset to blank
        }

        String input = scan.nextLine();
        while (!input.equals("bye")) {
            try {
                String[] tokens = input.split(" ");
                if (input.equals("list")) {
                    list();
                } else if (tokens[0].equals("mark")) {
                    setDone(tokens);
                } else if (tokens[0].equals("unmark")) {
                    setUndone(tokens);
                } else if (tokens[0].equals("todo")) {
                    todo(input);
                } else if (tokens[0].equals("deadline")) {
                    deadline(tokens);
                } else if (tokens[0].equals("event")) {
                    event(tokens);
                } else if (tokens[0].equals("delete")) {
                    delete(tokens);
                } else {
                    cannotParse();
                }
            } catch (AstraeaInputException ae) {
                ae.print();
            } finally {
                input = scan.nextLine();
            }
        }
        exit();
    }

    private static void intro() {
        System.out.println(separator);
        System.out.println("\t Astraea here. What do you want?");
        System.out.println(separator);
    }

    /*
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
     */

    private static void todo(String input) throws AstraeaInputException {
        if (input.length() <= 5) {
            throw new AstraeaInputException("todo_noName");
        }
        String name = input.substring(5);
        if (name.isBlank()) {
            throw new AstraeaInputException("todo_noName");
        }

        Todo task = new Todo(name);
        list.add(task);
        System.out.println(separator);
        System.out.println("\t Much ado about this todo.");
        System.out.println("\t   " + task);
        System.out.println("\t I'm tracking " + list.size() + " of your tasks now.");
        System.out.println(separator);

        try {
            saveNewLine(task);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void deadline(String[] inputs) throws AstraeaInputException {
        StringBuilder name = new StringBuilder();
        StringBuilder deadline = new StringBuilder();
        boolean deadlineFlag = false;
        for (int i = 1; i < inputs.length; i++) {
            if (inputs[i].equals("/by")) {
                deadlineFlag = true;
                continue;
            }
            if (deadlineFlag) {
                if (!deadline.isEmpty()) deadline.append(" ");
                deadline.append(inputs[i]);
            } else {
                if (!name.isEmpty()) name.append(" ");
                name.append(inputs[i]);
            }
        }
        if (name.isEmpty())
            throw new AstraeaInputException("deadline_noName");
        if (deadline.isEmpty())
            throw new AstraeaInputException("deadline_noTime");
        Deadline task = new Deadline(name.toString(), deadline.toString());
        list.add(task);
        System.out.println(separator);
        System.out.println("\t Time's ticking on this deadline. Get to it soon.");
        System.out.println("\t   " + task);
        System.out.println("\t I'm tracking " + list.size() + " of your tasks now.");
        System.out.println(separator);

        try {
            saveNewLine(task);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void event(String[] inputs) throws AstraeaInputException {
        StringBuilder name = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        short divider = 0;
        for (int i = 1; i < inputs.length; i++) {
            String input = inputs[i];
            if (input.equals("/from")) {
                divider = 1;
                continue;
            }
            if (input.equals("/to")) {
                divider = 2;
                continue;
            }
            if (divider == 2) {
                if (!end.isEmpty()) end.append(" ");
                end.append(input);
            } else if (divider == 1) {
                if (!start.isEmpty()) start.append(" ");
                start.append(input);
            } else {
                if (!name.isEmpty()) name.append(" ");
                name.append(input);
            }
        }
        if (name.isEmpty())
            throw new AstraeaInputException("event_noName");
        if (start.isEmpty())
            throw new AstraeaInputException("event_noStart");
        if (end.isEmpty())
            throw new AstraeaInputException("event_noEnd");
        Event task = new Event(name.toString(), start.toString(), end.toString());
        list.add(task);
        System.out.println(separator);
        System.out.println("\t A fleeting moment in time to be. Don't miss this.");
        System.out.println("\t   " + task);
        System.out.println("\t I'm tracking " + list.size() + " of your tasks now.");
        System.out.println(separator);

        try {
            saveNewLine(task);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void list() {
        if (list.isEmpty()) {
            System.out.println(separator);
            System.out.println("\t You don't have any tasks on my records.");
            System.out.println(separator);
            return;
        }
        System.out.println(separator);
        System.out.println("\t Let's see. You wanted to do these.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t " + (i + 1) + "." + list.get(i));
        }
        System.out.println(separator);
    }

    private static void setDone(String[] inputs) throws AstraeaInputException {
        if (inputs.length != 2 || !isNumeric(inputs[1])) {
            throw new AstraeaInputException("mark");
        }
        int index = Integer.parseInt(inputs[1]);
        try {
            list.get(index - 1).setDone();

            System.out.println(separator);
            System.out.println("\t Got that done, have you? Good.");
            System.out.println("\t   " + list.get(index - 1));
            System.out.println(separator);

            save();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(separator);
            System.out.println("\t The index you gave me is out of bounds. Try checking list.");
            System.out.println(separator);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void setUndone(String[] inputs) throws AstraeaInputException {
        if (inputs.length != 2 || !isNumeric(inputs[1])) {
            throw new AstraeaInputException("unmark");
        }
        int index = Integer.parseInt(inputs[1]);
        try {
            list.get(index - 1).setUndone();

            System.out.println(separator);
            System.out.println("\t Hm? Better get on that then.");
            System.out.println("\t   " + list.get(index - 1));
            System.out.println(separator);

            save();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(separator);
            System.out.println("\t The index you gave me is out of bounds. Try checking list.");
            System.out.println(separator);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void delete(String[] inputs) throws AstraeaInputException {
        if (inputs.length != 2 || !isNumeric(inputs[1])) {
            throw new AstraeaInputException("delete");
        }
        int index = Integer.parseInt(inputs[1]);
        try {
            Task task = list.remove(index - 1);

            System.out.println(separator);
            if (task.isDone()) {
                System.out.println("\t All done and dusted? Tidying that up then.");
            } else {
                System.out.println("\t A vanished opportunity, or running away?\n\t No matter. It's been removed.");
            }
            System.out.println("\t   " + task);
            System.out.println("\t I'm tracking " + list.size() + " of your tasks now.");
            System.out.println(separator);

            save();
        } catch (IndexOutOfBoundsException unused) {
            System.out.println(separator);
            System.out.println("\t The index you gave me is out of bounds. Try checking list.");
            System.out.println(separator);
        } catch (IOException exception) {
            System.out.println("\t Something went wrong with saving data.");
            System.out.println(exception.getMessage());
            System.out.println(separator);
        }
    }

    private static void cannotParse() throws AstraeaInputException {
        throw new AstraeaInputException("cannotParse");
    }

    private static void save() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt")));
        for (Task task : list) {
            pw.println(task.getSaveStyle());
        }
        pw.close();
    }

    private static void saveNewLine(Task task) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt", true)));
        pw.println(task.getSaveStyle());
        pw.close();
    }

    private static void read() throws IOException, AstraeaFileException {
        BufferedReader br = new BufferedReader(new FileReader("data/tasks.txt"));
        String line;
        Task task;
        while ((line = br.readLine()) != null) {
            String[] info = line.split("(\\s\\|\\s)");
            String type = info[0];
            boolean isDone = info[1].equals("1");
            String name = info[2];
            switch (type) {
            case "T":
                task = new Todo(name);
                break;
            case "D":
                String deadline = info[3];
                task = new Deadline(name, deadline);
                break;
            case "E":
                String start = info[3];
                String end = info[4];
                task = new Event(name, start, end);
                break;
            default:
                throw new AstraeaFileException("badFileRead");
            }
            list.add(task);
            if (isDone) {
                task.setDone();
            }
        }
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
