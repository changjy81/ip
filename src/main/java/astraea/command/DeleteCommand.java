package astraea.command;

import java.io.IOException;

import astraea.storage.Storage;
import astraea.task.Task;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

/**
 * Represents a command to delete a Task.
 * String[] args should contain only one numeric String representing the index of Task in TaskList to delete.
 */
public class DeleteCommand extends Command {
    public DeleteCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Parses the index String as Integer, then attempts to delete the Task from the given TaskList,
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
            Task task = list.remove(index - 1);
            String[] message = new String[]{
                "",
                "  " + task,
                "I'm tracking " + list.size() + " of your tasks now."
            };
            if (task.isDone()) {
                message[0] = "All done and dusted? Tidying that up then.";
            } else {
                message[0] = "A vanished opportunity, or running away?\n\t No matter. It's been removed.";
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
