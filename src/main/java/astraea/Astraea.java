package astraea;

import astraea.command.Command;
import astraea.command.ExitCommand;
import astraea.exception.AstraeaInputException;
import astraea.parser.Parser;
import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;

/**
 * Main class of Astraea.
 */
public class Astraea {
    private final AstraeaUi ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExit = false;

    private Astraea() {
        this.ui = new AstraeaUi();
        this.storage = new Storage();
        this.taskList = new TaskList();
    }

    private void run() {
        ui.intro();
        storage.load(ui, taskList);
        while (!isExit) {
            try {
                String input = ui.readInput();
                Command command = Parser.parseInput(input);
                if (command instanceof ExitCommand) {
                    isExit = true;
                }
                command.execute(taskList, storage, ui);
            } catch (AstraeaInputException ae) {
                ui.printBoundedMessage(ae.getErrorMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Astraea().run();
    }
}
