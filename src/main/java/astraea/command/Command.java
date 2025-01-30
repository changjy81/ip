package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

import java.util.Arrays;

public class Command {
    private final CommandType commandType;
    private final String[] args;

    public Command(CommandType commandType, String[] args) {
        this.commandType = commandType;
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getArguments() {
        return args;
    }

    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        // do nothing
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Command command) {
            return this.commandType == command.commandType && Arrays.equals(this.args, command.args);
        } else {
            return false;
        }
    }
}
