package astraea.parser;

import java.util.HashMap;
import java.util.Map;

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
        String command = Alias.findCommandOfAlias(tokens[0]);
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

    // All methods regarding Deadline and Event processing were rewritten by ChatGPT.

    private static String[] processDeadlineTokens(String[] tokens) throws AstraeaInputException {
        Map<String, StringBuilder> parsedTokens = parseDeadlineTokens(tokens);

        validateDeadlineTokens(parsedTokens);

        return new String[]{
            parsedTokens.get("name").toString(),
            parsedTokens.get("deadline").toString()
        };
    }

    private static Map<String, StringBuilder> parseDeadlineTokens(String[] tokens) {
        Map<String, StringBuilder> tokenMap = new HashMap<>();
        tokenMap.put("name", new StringBuilder());
        tokenMap.put("deadline", new StringBuilder());

        boolean isDeadline = false;

        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].equals("/by")) {
                isDeadline = true;
                continue;
            }
            appendToToken(tokenMap.get(isDeadline ? "deadline" : "name"), tokens[i]);
        }
        return tokenMap;
    }

    private static void validateDeadlineTokens(Map<String, StringBuilder> tokens) throws AstraeaInputException {
        if (tokens.get("name").isEmpty()) {
            throw new AstraeaInputException("deadline_noName");
        }
        if (tokens.get("deadline").isEmpty()) {
            throw new AstraeaInputException("deadline_noTime");
        }
    }

    private static String[] processEventTokens(String[] tokens) throws AstraeaInputException {
        Map<String, StringBuilder> parsedTokens = parseEventTokens(tokens);

        validateEventTokens(parsedTokens);

        return new String[]{
            parsedTokens.get("name").toString(),
            parsedTokens.get("start").toString(),
            parsedTokens.get("end").toString()
        };
    }

    private static Map<String, StringBuilder> parseEventTokens(String[] tokens) {
        Map<String, StringBuilder> tokenMap = new HashMap<>();
        tokenMap.put("name", new StringBuilder());
        tokenMap.put("start", new StringBuilder());
        tokenMap.put("end", new StringBuilder());

        String key = "name"; // Default to event name
        for (int i = 1; i < tokens.length; i++) {
            switch (tokens[i]) {
            case "/from" -> key = "start";
            case "/to" -> key = "end";
            default -> appendToToken(tokenMap.get(key), tokens[i]);
            }
        }
        return tokenMap;
    }

    private static void appendToToken(StringBuilder sb, String token) {
        if (!sb.isEmpty()) {
            sb.append(" ");
        }
        sb.append(token);
    }

    private static void validateEventTokens(Map<String, StringBuilder> tokens) throws AstraeaInputException {
        if (tokens.get("name").isEmpty()) {
            throw new AstraeaInputException("event_noName");
        }
        if (tokens.get("start").isEmpty()) {
            throw new AstraeaInputException("event_noStart");
        }
        if (tokens.get("end").isEmpty()) {
            throw new AstraeaInputException("event_noEnd");
        }
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
