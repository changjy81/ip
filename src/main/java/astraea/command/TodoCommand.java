package astraea.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.task.Todo;
import astraea.ui.AstraeaUi;

/**
 * Represents a command to create a Todo task.
 * String[] args should contain only one String representing the name of the Todo task.
 */
public class TodoCommand extends Command {
    public TodoCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Creates a Todo task with the given arguments, adds it to the TaskList, attempts to save to Storage
     * and prints to UI.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUi object to print to console.
     */
    @Override
    public String[] execute(TaskList list, Storage storage, AstraeaUi ui) {
        Todo task = new Todo(this.getArguments()[0]);
        list.add(task);
        String[] message = new String[]{
            "Much ado about this todo.",
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
