package astraea.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import astraea.storage.Storage;
import astraea.task.Deadline;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;

/**
 * Represents a command to create a Deadline task.
 * String[] args should only contain two Strings representing the name and deadline of the Deadline task.
 */
public class DeadlineCommand extends Command {
    public DeadlineCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Creates a Deadline task with the given arguments, adds it to the TaskList, attempts to save to Storage
     * and prints to UI.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUi object to print to console.
     */
    @Override
    public String[] execute(TaskList list, Storage storage, AstraeaUi ui) {
        Deadline task = Deadline.createDeadline(this.getArguments()[0], this.getArguments()[1]);
        list.add(task);
        String[] message = new String[]{
            "Time's ticking on this deadline. Get to it soon.",
            "  " + task,
            "I'm tracking " + list.size() + " of your tasks now."
        };
        try {
            storage.saveNewLine(task);
        } catch (IOException exception) {
            ArrayList<String> newMessage = new ArrayList<String>(Arrays.asList(message));
            newMessage.add("Something went wrong with saving data.");
            newMessage.add(exception.getMessage());
            message = newMessage.toArray(new String[0]);
        }
        ui.printBoundedMessage(message);
        return message;
    }
}
