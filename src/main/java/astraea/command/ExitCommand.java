package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

/**
 * Represents a command to exit the program.
 * String[] args should be null and should not be used.
 */
public class ExitCommand extends Command {
    public ExitCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Prints the exit message to UI.
     * Actual termination of the program is handled in the Astraea class.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUI object to print to console.
     */
    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        ui.exit();
    }
}
