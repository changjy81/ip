package astraea.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class for storing command aliases.
 */
public class Alias {
    // key should be alias, value should be corresponding command
    private static final HashMap<String, String> aliases = new HashMap<>();
    private static final String[] commands = new String[] {
        "list", "mark", "unmark", "todo", "deadline", "event", "delete", "find",
        "add_alias", "delete_alias", "bye"
    };

    public static void addAlias(String alias, String command) {
        aliases.put(alias, command);
    }

    public static String removeAlias(String alias) {
        return aliases.remove(alias);
    }

    public static boolean checkCommand(String command) {
        return Arrays.asList(commands).contains(command);
    }

    public static boolean checkAlias(String alias) {
        return aliases.containsKey(alias) || checkCommand(alias);
    }

    public static String findCommandOfAlias(String alias) {
        return aliases.getOrDefault(alias, alias);
    }

    public static ArrayList<String[]> getSaveStyle() {
        ArrayList<String[]> style = new ArrayList<>();
        for (HashMap.Entry<String, String> entry : aliases.entrySet()) {
            style.add(new String[] { entry.getKey(), entry.getValue() });
        }
        return style;
    }
}
