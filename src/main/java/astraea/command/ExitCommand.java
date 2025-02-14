package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;

/**
 * Represents a command to exit the program.
 * String[] args should be null and should not be used.
 */
public class ExitCommand extends Command {
    public ExitCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Does nothing.
     * Actual termination of the program is handled in the Astraea class.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @return null
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        return null;
    }
}
