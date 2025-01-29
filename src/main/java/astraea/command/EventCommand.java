package astraea.command;

import astraea.storage.Storage;
import astraea.task.Event;
import astraea.task.TaskList;
import astraea.ui.AstraeaUI;

import java.io.IOException;

public class EventCommand extends Command {
    public EventCommand(CommandType type, String[] args) {
        super(type, args);
    }

    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        Event task = Event.createEvent(this.getArguments()[0], this.getArguments()[1], this.getArguments()[2]);
        list.add(task);
        String[] message = new String[]{
                "A fleeting moment in time to be. Don't miss this.",
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
