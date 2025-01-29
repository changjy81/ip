public class ExitCommand extends Command {
    public ExitCommand(CommandType type, String[] args) {
        super(type, args);
    }

    @Override
    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        ui.exit();
    }
}
