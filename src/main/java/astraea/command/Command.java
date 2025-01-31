package astraea.command;

import java.util.Arrays;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

public class Command {
    private final CommandType commandType;
    private final String[] args;

    public Command(CommandType commandType, String[] args) {
        this.commandType = commandType;
        this.args = args;
    }

    /**
     * Returns CommandType associated with this Command.
     *
     * @return CommandType of this Command.
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Returns arguments associated with this Command.
     *
     * @return String[] containing stored arguments.
     */
    public String[] getArguments() {
        return args;
    }

    /**
     * Shell function for command execution. Does nothing in the base Command class.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUI object to print to console.
     */
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        // do nothing
    }

    /**
     * Implementation of equals method to compare Command objects by stored data instead of object ID.
     *
     * @param o Object to compare against.
     * @return Boolean value representing whether the object is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Command command) {
            return this.commandType == command.commandType && Arrays.equals(this.args, command.args);
        } else {
            return false;
        }
    }
}
