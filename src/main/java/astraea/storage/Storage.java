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

    private void read(TaskList list) throws IOException, AstraeaFileException {
        BufferedReader br = new BufferedReader(new FileReader("data/tasks.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            this.parseLine(line, list);
        }
    }

    private void parseLine(String line, TaskList list) throws AstraeaFileException {
        Task task;
        if (line.isBlank()) {
            return;
        }

        String[] info = line.split("(\\s\\|\\s)");
        String type = info[0];
        String name = info[2];
        switch (type) {
        case "T":
            task = new Todo(name);
            break;
        case "D":
            String deadline = info[3];
            task = Deadline.createDeadline(name, deadline);
            break;
        case "E":
            String start = info[3];
            String end = info[4];
            task = Event.createEvent(name, start, end);
            break;
        default:
            throw new AstraeaFileException("badFileRead");
        }
        list.add(task);

        boolean isDone = info[1].equals("1");
        if (isDone) {
            task.setDone();
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
     * @param list Empty TaskList object to populate.
     * @return Messages containing results to be printed as Astraea.
     */
    public String[] load(TaskList list) {
        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File("data/tasks.txt");
            String[] message;
            if (file.createNewFile()) {
                // no task save data found, created new file
                message = new String[]{"I have no data recorded. New storage file created"};
            } else {
                // read existing save data and repopulate list
                read(list);
                message = new String[]{"I've retrieved your tasks from last time."};
            }
            return message;
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
}
