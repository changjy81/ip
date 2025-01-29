public class Astraea {
    private final AstraeaUI ui;
    private final Storage storage;
    private final TaskList taskList;
    private final String savePath;
    private boolean isExit = false;

    private Astraea(String savePath) {
        this.savePath = savePath;
        this.ui = new AstraeaUI();
        this.storage = new Storage();
        this.taskList = new TaskList();
    }

    private void run() {
        ui.intro();
        storage.load(ui, taskList, savePath);
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
        new Astraea("data/tasks.txt").run();
    }
}
