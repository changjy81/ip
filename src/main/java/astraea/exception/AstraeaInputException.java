package astraea.exception;

/**
 * Represents an Exception relating to invalid user inputs.
 * String type should detail the context of the invalid input.
 */
public class AstraeaInputException extends Exception {
    private final String type;

    public AstraeaInputException(String type) {
        this.type = type;
    }

    /**
     * Returns the error message to be printed to UI associated with this exception.
     * @return String array of error messages.
     */
    public String[] getErrorMessage() {
        return switch (type) {
        case "todo_noName" -> new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: todo [name]"
        };
        case "deadline_noName" -> new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: deadline [name] /by [time]"
        };
        case "deadline_noTime" -> new String[]{
            "I can't tell when you need this done by. Did you forget the divider?",
            "Usage: deadline [name] /by [time]"
        };
        case "event_noName" -> new String[]{
            "You should probably give an actual name to your task, hm?",
            "Usage: event [name] /from [time] /to [time]"
        };
        case "event_noStart" -> new String[]{
            "I can't tell when this starts. Did you forget the divider?",
            "Usage: event [name] /from [time] /to [time]"
        };
        case "event_noEnd" -> new String[]{
            "I can't tell when this ends. Did you forget the divider?",
            "Usage: event [name] /from [time] /to [time]"
        };
        case "mark" -> new String[]{
            "Give me just one number for the index of the task.",
            "Usage: mark [index]"
        };
        case "unmark" -> new String[]{
            "Give me just one number for the index of the task.",
            "Usage: unmark [index]"
        };
        case "find_noName" -> new String[]{
            "If you wanted to search for blankness I'd suggest looking in your head.",
            "Usage: find [query]"
        };
        case "empty" -> new String[]{
            "I can't do anything with nothing, you know."
        };
        default -> new String[]{
            "What on earth are you blabbering about?",
            "Your first word isn't anything I can process."
        };
        };
    }

    /**
     * Implementation of equals method to compare AstraeaInputException objects by type.
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
