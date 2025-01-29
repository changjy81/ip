package astraea.parser;

import astraea.command.Command;
import astraea.command.CommandType;
import astraea.command.DeadlineCommand;
import astraea.command.DeleteCommand;
import astraea.command.EventCommand;
import astraea.command.ExitCommand;
import astraea.command.ListCommand;
import astraea.command.TodoCommand;
import astraea.command.ToggleCommand;
import astraea.exception.AstraeaInputException;

public class Parser {
    public static Command parseInput(String input) throws AstraeaInputException {
        if (input.isEmpty()) {
            throw new AstraeaInputException("empty");
        }
        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        return switch (command) {
            case "list" -> new ListCommand(CommandType.LIST, null);
            case "mark" -> new ToggleCommand(CommandType.MARK, processSingleNumberToken(tokens));
            case "unmark" -> new ToggleCommand(CommandType.UNMARK, processSingleNumberToken(tokens));
            case "todo" -> new TodoCommand(CommandType.TODO, processTodoTokens(tokens));
            case "deadline" -> new DeadlineCommand(CommandType.DEADLINE, processDeadlineTokens(tokens));
            case "event" -> new EventCommand(CommandType.EVENT, processEventTokens(tokens));
            case "delete" -> new DeleteCommand(CommandType.DELETE, processSingleNumberToken(tokens));
            case "bye" -> new ExitCommand(CommandType.EXIT, null);
            default -> throw new AstraeaInputException("invalid");
        };
    }

    private static String[] processSingleNumberToken(String[] tokens) throws AstraeaInputException {
        if (isNumeric(tokens[1]) && tokens.length == 2) {
            return new String[] { tokens[1] };
        } else {
            throw new AstraeaInputException(tokens[0]);
        }
    }

    private static String[] processTodoTokens(String[] tokens) throws AstraeaInputException {
        if (tokens.length < 2) {
            throw new AstraeaInputException("todo_noName");
        } else {
            StringBuilder name = new StringBuilder();
            for (int i = 1; i < tokens.length; i++) {
                name.append(tokens[i]);
            }
            return new String[] { name.toString() };
        }
    }

    private static String[] processDeadlineTokens(String[] tokens) throws AstraeaInputException {
        StringBuilder name = new StringBuilder();
        StringBuilder deadline = new StringBuilder();
        boolean deadlineFlag = false;

        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].equals("/by")) {
                deadlineFlag = true;
                continue;
            }
            if (deadlineFlag) {
                if (!deadline.isEmpty()) deadline.append(" ");
                deadline.append(tokens[i]);
            } else {
                if (!name.isEmpty()) name.append(" ");
                name.append(tokens[i]);
            }
        }

        if (name.isEmpty()) {
            throw new AstraeaInputException("deadline_noName");
        }
        if (deadline.isEmpty()) {
            throw new AstraeaInputException("deadline_noTime");
        }

        return new String[] { name.toString(), deadline.toString() };
    }

    private static String[] processEventTokens(String[] tokens) throws AstraeaInputException {
        StringBuilder name = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        short divider = 0;

        for (int i = 1; i < tokens.length; i++) {
            String input = tokens[i];
            if (input.equals("/from")) {
                divider = 1;
                continue;
            }
            if (input.equals("/to")) {
                divider = 2;
                continue;
            }
            if (divider == 2) {
                if (!end.isEmpty()) end.append(" ");
                end.append(input);
            } else if (divider == 1) {
                if (!start.isEmpty()) start.append(" ");
                start.append(input);
            } else {
                if (!name.isEmpty()) name.append(" ");
                name.append(input);
            }
        }

        if (name.isEmpty())
            throw new AstraeaInputException("event_noName");
        if (start.isEmpty())
            throw new AstraeaInputException("event_noStart");
        if (end.isEmpty())
            throw new AstraeaInputException("event_noEnd");

        return new String[] { name.toString(), start.toString(), end.toString() };
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
