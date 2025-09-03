package astraea.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Exception relating to invalid user inputs.
 * String type should detail the context of the invalid input.
 */
@SuppressWarnings("checkstyle:Indentation")
public class AstraeaInputException extends Exception {
    private static final Map<String, String[]> ERROR_MESSAGES = new HashMap<>();
    private final String type;

    // extracted out of getErrorMessage method by suggestion from ChatGPT
    static {
        ERROR_MESSAGES.put("todo_noName", new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: todo [name]"
        });
        ERROR_MESSAGES.put("deadline_noName", new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: deadline [name] /by [time]"
        });
        ERROR_MESSAGES.put("deadline_noTime", new String[]{
            "I can't tell when you need this done by. Did you forget the divider?",
            "Usage: deadline [name] /by [time]"
        });
        ERROR_MESSAGES.put("event_noName", new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: event [name] /from [time] /to [time]"
        });
        ERROR_MESSAGES.put("event_noStart", new String[]{
            "I can't tell when this starts. Did you forget the divider?",
            "Usage: event [name] /from [time] /to [time]"
        });
        ERROR_MESSAGES.put("event_noEnd", new String[]{
            "I can't tell when this ends. Did you forget the divider?",
            "Usage: event [name] /from [time] /to [time]"
        });
        ERROR_MESSAGES.put("mark", new String[]{
            "Give me just one number for the index of the task.",
            "Usage: mark [index]"
        });
        ERROR_MESSAGES.put("unmark", new String[]{
            "Give me just one number for the index of the task.",
            "Usage: unmark [index]"
        });
        ERROR_MESSAGES.put("find_noName", new String[]{
            "If you wanted to search for blankness I'd suggest looking in your head.",
            "Usage: find [query]"
        });
        ERROR_MESSAGES.put("add_alias_wrongUsage", new String[]{
            "In some universes, trying to give a name so carelessly would get you killed.",
            "Usage: add_alias [command] [name]"
        });
        ERROR_MESSAGES.put("add_alias_invalidCommand", new String[]{
            "That's not even a command you're trying to rename.",
            "Usage: add_alias [command] [name]"
        });
        ERROR_MESSAGES.put("add_alias_existingName", new String[]{
            "Do you like giving all of your things the same name? Hopefully not your passwords...",
            "Usage: add_alias [command] [name]"
        });
        ERROR_MESSAGES.put("remove_alias_wrongUsage", new String[]{
            "You say you named that? You might be hallucinating.",
            "Usage: remove_alias [name]"
        });
        ERROR_MESSAGES.put("empty", new String[]{
            "I can't do anything with nothing, you know."
        });
        ERROR_MESSAGES.put("pipeChar", new String[]{
            "The divine scriptures restrict the use of the pipe | character.",
            "Don't use it, unless you like your data becoming an eldritch horror."
        });
    }

    public AstraeaInputException(String type) {
        this.type = type;
    }

    /**
     * Returns the error message to be printed to UI associated with this exception.
     *
     * @return String array of error messages.
     */
    public String[] getErrorMessage() {
        return ERROR_MESSAGES.getOrDefault(type, new String[]{
            "What on earth are you blabbering about?",
            "Your first word isn't anything I can process."
        });
    }

    /**
     * Implementation of equals method to compare AstraeaInputException objects by type.
     *
     * @param obj Object to compare against.
     * @return Boolean value representing whether the object is equal to this.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AstraeaInputException other) {
            return this.type.equals(other.type);
        } else {
            return false;
        }
    }
}
