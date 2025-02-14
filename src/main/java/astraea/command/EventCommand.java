package astraea.command;

import astraea.storage.Storage;
import astraea.task.Event;
import astraea.task.TaskList;

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
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        Event task = Event.createEvent(this.getArguments()[0], this.getArguments()[1], this.getArguments()[2]);
        list.add(task);
        String[] message = new String[]{
            "A fleeting moment in time to be. Don't miss this.",
            "  " + task,
            "I'm tracking " + list.size() + " of your tasks now."
        };
        message = storage.saveTaskWithHandling(task, message);
        return message;
    }
}
