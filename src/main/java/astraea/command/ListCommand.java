package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

import java.util.ArrayList;

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
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUI object to print to console.
     */
    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        if (list.isEmpty()) {
            ui.printBoundedMessage("You don't have any tasks on my records.");
            return;
        }

        ArrayList<String> message = new ArrayList<String>();
        message.add("Let's see. You wanted to do these.");
        for (int i = 0; i < list.size(); i++) {
            message.add(" " + (i + 1) + "." + list.get(i));
        }
        ui.printBoundedMessage(message.toArray(new String[0]));
    }
}
