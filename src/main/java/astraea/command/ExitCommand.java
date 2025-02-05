package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;

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
     * @param ui AstraeaUi object to print to console.
     */
    @Override
    public String[] execute(TaskList list, Storage storage, AstraeaUi ui) {
        return ui.exit();
    }
}
