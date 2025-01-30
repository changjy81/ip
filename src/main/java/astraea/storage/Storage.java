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

import astraea.exception.AstraeaFileException;
import astraea.task.Deadline;
import astraea.task.Event;
import astraea.task.Task;
import astraea.task.TaskList;
import astraea.task.Todo;
import astraea.ui.AstraeaUI;

public class Storage {
    private static void read(TaskList list) throws IOException, AstraeaFileException {
        BufferedReader br = new BufferedReader(new FileReader("data/tasks.txt"));
        String line;
        Task task;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                break;
            }
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
            if (isDone) {
                task.setDone();
            }
        }
    }

    public void save(TaskList list) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt")));
        for (Task task : list) {
            pw.println(task.getSaveStyle());
        }
        pw.close();
    }

    public void saveNewLine(Task task) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/tasks.txt", true)));
        pw.println(task.getSaveStyle());
        pw.close();
    }

    public void load(AstraeaUI ui, TaskList list, String savePath) {
        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File(savePath);
            if (file.createNewFile()) {
                // no task save data found, created new file
                ui.printBottomBoundedMessage("I have no data recorded. New storage file created.");
            } else {
                // read existing save data
                read(list);
                ui.printBottomBoundedMessage("I've retrieved your tasks from last time.");
            }
        } catch (IOException e) {
            ui.printBoundedMessage(e.getMessage());
        } catch (AstraeaFileException ae) {
            // invalid/corrupted data
            // TODO copy bad data into new file, reset to blank
        }
    }
}
