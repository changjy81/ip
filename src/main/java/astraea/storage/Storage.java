package astraea.storage;

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
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import astraea.command.Alias;
import astraea.exception.AstraeaFileException;
import astraea.task.Deadline;
import astraea.task.Event;
import astraea.task.Task;
import astraea.task.TaskList;
import astraea.task.Todo;


/**
 * Represents the object used to read from and write to files.
 */
public class Storage {
    private static final String TASKS_FILEPATH = "data/tasks.txt";
    private static final String ALIAS_FILEPATH = "data/aliases.txt";

    private void read(TaskList list) throws IOException, AstraeaFileException {
        ArrayList<String> inputs = readFile(TASKS_FILEPATH);
        Function<String, Task> makeTask = input -> {
            try {
                return createTask(input.split("(\\s\\|\\s)"));
            } catch (AstraeaFileException e) {
                throw new RuntimeException(e);
            }
        };
        inputs.stream().map(makeTask).forEach(list::add);
    }

    private Task createTask(String[] input) throws AstraeaFileException {
        String type = input[0];
        String name = input[2];
        Task task;
        switch (type) {
        case "T":
            task = new Todo(name);
            break;
        case "D":
            String deadline = input[3];
            task = Deadline.createDeadline(name, deadline);
            break;
        case "E":
            String start = input[3];
            String end = input[4];
            task = Event.createEvent(name, start, end);
            break;
        default:
            throw new AstraeaFileException("badFileRead");
        }
        if (input[1].equals("1")) {
            task.setDone();
        }
        return task;
    }

    /**
     * Saves the current state of TaskList to the tasks.txt file.
     *
     * @param list TaskList to read and save.
     * @throws IOException Thrown if an I/O exception occurs.
     */
    public void save(TaskList list) throws IOException {
        assert list != null : "Null list being saved";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKS_FILEPATH)));
        for (Task task : list) {
            pw.println(task.getSaveStyle());
        }
        pw.close();
    }

    /**
     * Appends the given Task to the tasks.txt file.
     *
     * @param task Task to be saved.
     * @throws IOException Thrown if an I/O exception occurs.
     */
    public void saveNewLine(Task task) throws IOException {
        assert task != null : "Null task being saved";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKS_FILEPATH, true)));
        pw.println(task.getSaveStyle());
        pw.close();
    }

    /**
     * Reads the tasks.txt file and reconstructs the saved TaskList.
     * Also reads aliases.txt to reconstruct aliases.
     * Run on program initialization.
     *
     * @param list Empty TaskList object to populate.
     * @return Messages containing results to be printed as Astraea.
     */
    public String[] load(TaskList list) {
        assert list != null : "Null TaskList object";
        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File(TASKS_FILEPATH);
            StringBuilder message = new StringBuilder();
            if (file.createNewFile()) {
                // no task save data found, created new file
                message.append("I have no data recorded. New storage file created.");
            } else {
                // read existing save data and repopulate list
                read(list);
                message.append("I've retrieved your tasks from last time.");
            }

            file = new File(ALIAS_FILEPATH);
            if (file.createNewFile()) {
                message.append("\nI have no aliases recorded. New storage file created.");
            } else {
                readAliases();
                message.append("\nI've also retrieved your aliases from last time.");
            }
            return message.toString().split("\n");
        } catch (IOException e) {
            return new String[]{e.getMessage()};
        } catch (AstraeaFileException ae) {
            // invalid/corrupted data
            // TODO copy bad data into new file, reset to blank
            return new String[]{"I ran into a file exception."};
        }
    }

    /**
     * Attempts to save the Task in this Storage, with IOException handling to ensure the appropriate UI message
     * is returned. Used in all Task-creation related Command subclasses.
     *
     * @param task Task to be saved.
     * @return String message that is modified only if an exception occurs.
     */
    public String[] saveTaskWithHandling(Task task, String[] message) {
        try {
            this.saveNewLine(task);
        } catch (IOException exception) {
            ArrayList<String> newMessage = new ArrayList<>(Arrays.asList(message));
            newMessage.add("Something went wrong with saving data.");
            newMessage.add(exception.getMessage());
            message = newMessage.toArray(new String[0]);
        }
        return message;
    }

    /**
     * Appends the given Task to the aliases.txt file.
     *
     * @param alias Alias to be saved.
     * @param command Command type associated with the alias.
     * @throws IOException Thrown if an I/O exception occurs.
     */
    public void saveNewAlias(String alias, String command) throws IOException {
        assert alias != null : "Null alias being saved";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ALIAS_FILEPATH, true)));
        pw.println(alias + " | " + command);
        pw.close();
    }

    /**
     * Saves the current aliases to the aliases.txt file.
     *
     * @throws IOException Thrown if an I/O exception occurs.
     */
    public void saveAlias() throws IOException {
        ArrayList<String[]> style = Alias.getSaveStyle();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ALIAS_FILEPATH)));
        for (String[] line : style) {
            pw.println(line[0] + " | " + line[1]);
        }
        pw.close();
    }

    private void readAliases() throws IOException {
        ArrayList<String> inputs = readFile(ALIAS_FILEPATH);
        Consumer<String> remakeAliases = input -> {
            String[] tokens = input.split("(\\s\\|\\s)");
            Alias.addAlias(tokens[0], tokens[1]);
        };
        inputs.forEach(remakeAliases);
    }

    private ArrayList<String> readFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        ArrayList<String> inputs = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                break;
            }
            inputs.add(line);
        }
        return inputs;
    }
}
