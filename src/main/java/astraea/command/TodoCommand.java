package astraea.command;

import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.task.Todo;
import astraea.ui.AstraeaUI;

import java.io.IOException;

public class TodoCommand extends Command {
    public TodoCommand(CommandType type, String[] args) {
        super(type, args);
    }

    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        Todo task = new Todo(this.getArguments()[0]);
        list.add(task);
        String[] message = new String[]{
                "Much ado about this todo.",
                "  " + task,
                "I'm tracking " + list.size() + " of your tasks now."
        };
        ui.printBoundedMessage(message);

        try {
            storage.saveNewLine(task);
        } catch (IOException exception) {
            message = new String[]{
                    "Something went wrong with saving data.",
                    exception.getMessage()
            };
            ui.printBoundedMessage(message);
        }
    }
}
