package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.task.Todo;

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
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        Todo task = new Todo(this.getArguments()[0]);
        list.add(task);
        String[] message = new String[]{
            "Much ado about this todo.",
            "  " + task,
            "I'm tracking " + list.size() + " of your tasks now."
        };
        message = storage.saveTaskWithHandling(task, message);
        return message;
    }
}
