package astraea.command;

import java.util.ArrayList;

import astraea.storage.Storage;
import astraea.task.TaskList;

/**
 * Represents a command to list all tasks from TaskList.
 * String[] args should be null and should not be used.
 */
public class ListCommand extends Command {
    public ListCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Reads the given TaskList and prints all Tasks to UI.
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
        if (list.isEmpty()) {
            return new String[]{"You don't have any tasks on my records."};
        }

        ArrayList<String> message = new ArrayList<String>();
        message.add("Let's see. You wanted to do these.");
        for (int i = 0; i < list.size(); i++) {
            message.add(" " + (i + 1) + "." + list.get(i));
        }
        return message.toArray(new String[0]);
    }
}
