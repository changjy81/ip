package astraea.command;

import java.io.IOException;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

/**
 * Represents a command to change the isDone state of a Task.
 * String[] args should contain only one numeric String representing the index of Task in TaskList to modify.
 */
public class ToggleCommand extends Command {
    public ToggleCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Parses the index String as Integer, then attempts to modify the Task from the given TaskList,
     * then saves the new state of TaskList to Storage and prints to UI.
     * If the index provided is out of bounds, aborts execution and prints an error message.
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @param ui AstraeaUI object to print to console.
     */
    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        int index = Integer.parseInt(this.getArguments()[0]);
        try {
            String[] message;
            if (this.getCommandType() == CommandType.MARK) {
                list.get(index - 1).setDone();
                message = new String[]{
                    "Got that done, have you? Good.",
                    "  " + list.get(index - 1)
                };
            } else if (this.getCommandType() == CommandType.UNMARK) {
                list.get(index - 1).setUndone();
                message = new String[]{
                    "Hm? Better get on that then.",
                    "  " + list.get(index - 1)
                };
            } else {
                message = new String[]{"Something went wrong with this command."};
            }
            ui.printBoundedMessage(message);
            storage.save(list);
        } catch (IndexOutOfBoundsException e) {
            ui.printBoundedMessage("The index you gave me is out of bounds. Try checking list.");
        } catch (IOException exception) {
            ui.printBoundedMessage("Something went wrong with saving data.");
        }
    }

}
