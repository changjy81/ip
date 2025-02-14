package astraea.parser;

import astraea.command.AddAliasCommand;
import astraea.command.Alias;
import astraea.command.Command;
import astraea.command.CommandType;
import astraea.command.DeadlineCommand;
import astraea.command.DeleteCommand;
import astraea.command.EventCommand;
import astraea.command.ExitCommand;
import astraea.command.FindCommand;
import astraea.command.ListCommand;
import astraea.command.RemoveAliasCommand;
import astraea.command.TodoCommand;
import astraea.command.ToggleCommand;
import astraea.exception.AstraeaInputException;

/**
 * Class containing static methods associated with parsing user input into Commands.
 */
public class Parser {
    /**
     * Attempts to parse the user input String into the appropriate Command.
     * This is done by splitting the Command by whitespaces and reading the first word.
     * If necessary, all subsequent words are then processed and packaged into a String array representing
     * the arguments associated with the Command.
     *
     * @param input String containing the entire line of input from the user.
     * @return Command representing the action requested by user input.
     * @throws AstraeaInputException Thrown if the input is invalid, with different types based on the nature
     *                               of the invalid input.
     */
    public static Command parseInput(String input) throws AstraeaInputException {
        if (input.isEmpty()) {
            throw new AstraeaInputException("empty");
        }
        String[] tokens = input.split("\\s+");
        String command = checkAlias(tokens[0]);
        return switch (command) {
        case "list" -> new ListCommand(CommandType.LIST, null);
        case "mark" -> new ToggleCommand(CommandType.MARK, processSingleNumberToken(tokens));
        case "unmark" -> new ToggleCommand(CommandType.UNMARK, processSingleNumberToken(tokens));
        case "todo" -> new TodoCommand(CommandType.TODO, processTodoTokens(tokens));
        case "deadline" -> new DeadlineCommand(CommandType.DEADLINE, processDeadlineTokens(tokens));
        case "event" -> new EventCommand(CommandType.EVENT, processEventTokens(tokens));
        case "delete" -> new DeleteCommand(CommandType.DELETE, processSingleNumberToken(tokens));
        case "find" -> new FindCommand(CommandType.FIND, processFindTokens(tokens));
        case "add_alias" -> new AddAliasCommand(CommandType.ADD_ALIAS, processAddAliasTokens(tokens));
        case "remove_alias" -> new RemoveAliasCommand(CommandType.REMOVE_ALIAS, processRemoveAliasTokens(tokens));
        case "bye" -> new ExitCommand(CommandType.EXIT, null);
        default -> throw new AstraeaInputException("invalid");
        };
    }

    private static String checkAlias(String input) {
        return Alias.findCommandOfAlias(input);
    }

    private static String[] processSingleNumberToken(String[] tokens) throws AstraeaInputException {
        if (tokens.length == 2 && isNumeric(tokens[1])) {
            return new String[]{ tokens[1] };
        } else {
            throw new AstraeaInputException(tokens[0]);
        }
    }

    private static String[] processTodoTokens(String[] tokens) throws AstraeaInputException {
        if (tokens.length < 2) {
            throw new AstraeaInputException("todo_noName");
        } else {
            return getSingleStringOutput(tokens);
        }
    }

    private static String[] processFindTokens(String[] tokens) throws AstraeaInputException {
        if (tokens.length < 2) {
            throw new AstraeaInputException("find_noName");
        } else {
            return getSingleStringOutput(tokens);
        }
    }

    private static String[] getSingleStringOutput(String[] tokens) {
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < tokens.length; i++) {
            name.append(tokens[i]);
            name.append(" ");
        }
        name.deleteCharAt(name.length() - 1);
        return new String[] { name.toString() };
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
                if (!deadline.isEmpty()) {
                    deadline.append(" ");
                }
                deadline.append(tokens[i]);
            } else {
                if (!name.isEmpty()) {
                    name.append(" ");
                }
                name.append(tokens[i]);
            }
        }

        if (name.isEmpty()) {
            throw new AstraeaInputException("deadline_noName");
        }
        if (deadline.isEmpty()) {
            throw new AstraeaInputException("deadline_noTime");
        }

        return new String[]{ name.toString(), deadline.toString() };
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
                if (!end.isEmpty()) {
                    end.append(" ");
                }
                end.append(input);
            } else if (divider == 1) {
                if (!start.isEmpty()) {
                    start.append(" ");
                }
                start.append(input);
            } else {
                if (!name.isEmpty()) {
                    name.append(" ");
                }
                name.append(input);
            }
        }

        if (name.isEmpty()) {
            throw new AstraeaInputException("event_noName");
        }
        if (start.isEmpty()) {
            throw new AstraeaInputException("event_noStart");
        }
        if (end.isEmpty()) {
            throw new AstraeaInputException("event_noEnd");
        }

        return new String[] { name.toString(), start.toString(), end.toString() };
    }

    private static String[] processAddAliasTokens(String[] tokens) throws AstraeaInputException {
        if (tokens.length != 3) {
            throw new AstraeaInputException("add_alias_wrongUsage");
        } else if (!Alias.checkCommand(tokens[1])) {
            throw new AstraeaInputException("add_alias_invalidCommand");
        } else if (Alias.checkCommand(tokens[2])) {
            throw new AstraeaInputException("add_alias_existingName");
        }
        return new String[] { tokens[1], tokens[2] };
    }

    private static String[] processRemoveAliasTokens(String[] tokens) throws AstraeaInputException {
        if (tokens.length != 2) {
            throw new AstraeaInputException("remove_alias_wrongUsage");
        } else if (!Alias.checkAlias(tokens[1])) {
            throw new AstraeaInputException("remove_alias_wrongUsage");
        }
        return new String[] { tokens[1] };
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
