import java.util.ArrayList;

public class ListCommand extends Command {
    public ListCommand(CommandType type, String[] args) {
        super(type, args);
    }

    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        if (list.isEmpty()) {
            ui.printBoundedMessage("You don't have any tasks on my records.");
            return;
        }

        ArrayList<String> message = new ArrayList<String>();
        message.add("Let's see. You wanted to do these.");
        for (int i = 0; i < list.size(); i++) {
            message.add(" " + (i + 1) + "." + list.get(i));
        }
        ui.printBoundedMessage(message.toArray(new String[0]));
    }
}
