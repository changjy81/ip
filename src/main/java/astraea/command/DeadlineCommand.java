package astraea.command;

import astraea.storage.Storage;
import astraea.task.Deadline;
import astraea.task.TaskList;

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
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        Deadline task = Deadline.createDeadline(this.getArguments()[0], this.getArguments()[1]);
        list.add(task);
        String[] message = new String[]{
            "Time's ticking on this deadline. Get to it soon.",
            "  " + task,
            "I'm tracking " + list.size() + " of your tasks now."
        };
        message = storage.saveTaskWithHandling(task, message);
        return message;
    }
}
