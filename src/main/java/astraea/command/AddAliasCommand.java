package astraea.command;

import java.io.IOException;

import astraea.storage.Storage;
import astraea.task.TaskList;

/**
 * Represents a command to create a new alias.
 * String[] args should only contain two Strings representing the command type to be given the alias and the alias name.
 */
public class AddAliasCommand extends Command {
    public AddAliasCommand(CommandType commandType, String[] args) {
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
        Alias.addAlias(args[1], args[0]);
        try {
            storage.saveNewAlias(args[1], args[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String[]{
            "Mortal names are interesting. Meaningless, but interesting.",
            "Command type " + args[0] + " is now associated with " + args[1] + "."
        };
    }
}
