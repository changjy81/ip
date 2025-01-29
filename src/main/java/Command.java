public class Command {
    private final CommandType commandType;
    private final String[] args;

    public Command(CommandType commandType, String[] args) {
        this.commandType = commandType;
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getArguments() {
        return args;
    }

    public void execute(TaskList list, Storage storage, AstraeaUI ui) {
        // do nothing
    }
}
