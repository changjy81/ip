package astraea.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Stores command aliases and allows for new aliases to be created.
 */
public class Alias {
    // key should be alias, value should be corresponding command
    private static final HashMap<String, String> aliases = new HashMap<>();

    /**
     * Adds a new alias for one of the commands.
     *
     * @param alias String representing name of the alias.
     * @param command String representing the command type to be given an alias.
     */
    public static void addAlias(String alias, String command) {
        aliases.put(alias, command);
    }

    /**
     * Removes an existing alias for one of the commands.
     *
     * @param alias String representing name of the alias.
     */
    public static String removeAlias(String alias) {
        return aliases.remove(alias);
    }

    /**
     * Checks if a given String is a valid type of Command.
     *
     * @param command String representing the command to be checked.
     * @return true if the command argument is a valid type.
     */
    public static boolean checkCommand(String command) {
        return Arrays.asList(Command.NAMES).contains(command);
    }

    /**
     * Checks if a given alias already exists, or is one of the Command types.
     *
     * @param alias String representing the alias to be checked.
     * @return true if the alias argument is unique.
     */
    public static boolean checkAlias(String alias) {
        return aliases.containsKey(alias) || checkCommand(alias);
    }

    /**
     * Returns the command type associated with a given alias.
     *
     * @param alias String representing the alias to be checked.
     * @return The command type associated with the alias argument, or the alias argument itself if the alias cannot be
     *         found.
     */
    public static String findCommandOfAlias(String alias) {
        return aliases.getOrDefault(alias, alias);
    }

    /**
     * Constructs an ArrayList of String arrays for use in saving the current aliases in Storage.
     *
     * @return ArrayList of String[] containing all current alias information.
     */
    public static ArrayList<String[]> getSaveStyle() {
        ArrayList<String[]> style = new ArrayList<>();
        for (HashMap.Entry<String, String> entry : aliases.entrySet()) {
            style.add(new String[] { entry.getKey(), entry.getValue() });
        }
        return style;
    }
}
