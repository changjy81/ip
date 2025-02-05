package astraea.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import astraea.storage.Storage;
import astraea.task.Event;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;

/**
 * Represents a command to create an Event task.
 * String[] args should contain only three Strings representing the name, start time and end time of the
 * Event task.
 */
public class EventCommand extends Command {
    public EventCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Creates an Event task with the given arguments, adds it to the TaskList, attempts to save to Storage
     * and prints to UI.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUi object to print to console.
     */
    @Override
    public String[] execute(TaskList list, Storage storage, AstraeaUi ui) {
        Event task = Event.createEvent(this.getArguments()[0], this.getArguments()[1], this.getArguments()[2]);
        list.add(task);
        String[] message = new String[]{
            "A fleeting moment in time to be. Don't miss this.",
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
