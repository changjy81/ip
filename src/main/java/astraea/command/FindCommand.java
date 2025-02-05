package astraea.command;

import java.util.ArrayList;

import astraea.storage.Storage;
import astraea.task.Task;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;

/**
 * Represents a command to find tasks based on name.
 * String[] args should contain one String representing the query to search.
 */
public class FindCommand extends Command {
    public FindCommand(CommandType type, String[] args) {
        super(type, args);
    }

    /**
     * Runs the search on TaskList for Tasks containing the given search query.
     *
     * @param list TaskList object to search through.
     * @param storage Not used in this method.
     * @param ui AstraeaUi object to print results to.
     */
    @Override
    public String[] execute(TaskList list, Storage storage, AstraeaUi ui) {
        ArrayList<Task> foundList = new ArrayList<Task>();
        for (Task task : list) {
            if (task.getTaskName().contains(this.getArguments()[0])) {
                foundList.add(task);
            }
        }

        String[] message;
        if (foundList.isEmpty()) {
            message = new String[] { "I didn't find any tasks with that." };
        } else {
            message = new String[foundList.size() + 1];
            message[0] = "Here, I found these. You're welcome.";
            for (int i = 1; i <= foundList.size(); i++) {
                message[i] = foundList.get(i - 1).toString();
            }
        }
        ui.printBoundedMessage(message);
        return message;
    }
}
