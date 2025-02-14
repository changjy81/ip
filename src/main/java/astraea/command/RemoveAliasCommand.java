package astraea.command;

import java.io.IOException;

import astraea.storage.Storage;
import astraea.task.TaskList;

/**
 * Represents a command to remove an alias.
 * String[] args should only contain one String representing the alias to be removed.
 */
public class RemoveAliasCommand extends Command {
    public RemoveAliasCommand(CommandType commandType, String[] args) {
        super(commandType, args);
    }

    /**
     * Creates a new alias for the given type of command.
     *
     * @param list Not used in this method.
     * @param storage Storage to save the new alias to a file.
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        String[] args = this.getArguments();
        String type = Alias.removeAlias(args[0]);
        try {
            storage.saveAlias();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String[]{
            "Mortals have a saying of 'Gone but not forgotten'. It seems this one's fated to fade away.",
            "Command type " + args[0] + " is no longer associated with " + type + "."
        };
    }
}
