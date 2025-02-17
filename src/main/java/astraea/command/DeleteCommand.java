package astraea.command;

import java.io.IOException;

import astraea.storage.Storage;
import astraea.task.Task;
import astraea.task.TaskList;

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
     *
     * @param list TaskList object to access and/or modify.
     * @param storage Storage object to read/write data files.
     * @return Messages containing results to be printed as Astraea.
     */
    @Override
    public String[] execute(TaskList list, Storage storage) {
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
            storage.save(list);
            return message;
        } catch (IndexOutOfBoundsException e) {
            return new String[]{"The index you gave me is out of bounds. Try checking list."};
        } catch (IOException exception) {
            return new String[]{"Something went wrong with saving data."};
        }
    }
}
