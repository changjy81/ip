package astraea.command;

import astraea.task.Deadline;
import astraea.ui.AstraeaUI;

import astraea.storage.Storage;
import astraea.task.TaskList;
import java.io.IOException;

public class DeadlineCommand extends Command {
    public DeadlineCommand(CommandType type, String[] args) {
        super(type, args);
    }

    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        Deadline task = Deadline.createDeadline(this.getArguments()[0], this.getArguments()[1]);
        list.add(task);
        String[] message = new String[]{
                "Time's ticking on this deadline. Get to it soon.",
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
