import java.io.IOException;

public class ToggleCommand extends Command {
    public ToggleCommand(CommandType type, String[] args) {
        super(type, args);
    }

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
                message = new String[]{ "Something went wrong with this command." };
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
