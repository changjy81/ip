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
import java.util.function.Function;

import astraea.exception.AstraeaFileException;
import astraea.task.Deadline;
import astraea.task.Event;
import astraea.task.Task;
import astraea.task.TaskList;
import astraea.task.Todo;
import astraea.ui.AstraeaUi;


/**
 * Represents the object used to read from and write to files.
 */
public class Storage {

    private void read(TaskList list) throws IOException, AstraeaFileException {
        BufferedReader br = new BufferedReader(new FileReader("data/tasks.txt"));
        String line;
        ArrayList<String> inputs = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                break;
            }
            inputs.add(line);
        }
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
        String name = input[1];
        switch (type) {
        case "T":
            return new Todo(name);
        case "D":
            String deadline = input[3];
            return Deadline.createDeadline(name, deadline);
        case "E":
            String start = input[3];
            String end = input[4];
            return Event.createEvent(name, start, end);
        default:
            throw new AstraeaFileException("badFileRead");
        }
    }

    /**
     * Saves the current state of TaskList to the tasks.txt file.
     *
     * @param list TaskList to read and save.
     * @throws IOException Thrown if an I/O exception occurs.
     */
    public void save(TaskList list) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt")));
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
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt", true)));
        pw.println(task.getSaveStyle());
        pw.close();
    }

    /**
     * Reads the tasks.txt file and reconstructs the saved TaskList.
     * Run on program initialization.
     *
     * @param ui AstraeaUi object to print to console.
     * @param list Empty TaskList object to populate.
     */
    public String[] load(AstraeaUi ui, TaskList list) {
        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File("data/tasks.txt");
            String[] message;
            if (file.createNewFile()) {
                // no task save data found, created new file
                ui.printBottomBoundedMessage("I have no data recorded. New storage file created.");
                message = new String[]{"I have no data recorded. New storage file created"};
            } else {
                // read existing save data
                read(list);
                ui.printBottomBoundedMessage("I've retrieved your tasks from last time.");
                message = new String[]{"I've retrieved your tasks from last time."};
            }
            return message;
        } catch (IOException e) {
            ui.printBoundedMessage(e.getMessage());
            return new String[]{e.getMessage()};
        } catch (AstraeaFileException ae) {
            // invalid/corrupted data
            // TODO copy bad data into new file, reset to blank
            return new String[]{"I ran into a file exception."};
        }
    }
}
