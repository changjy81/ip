public class AstraeaInputException extends Exception {
    private final String type;

    public AstraeaInputException(String type) {
        this.type = type;
    }

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
            case "empty" -> new String[]{
                    "I can't do anything with nothing, you know."
            };
            default -> new String[]{
                    "What on earth are you blabbering about?",
                    "Your first word isn't anything I can process."
            };
        };
    }
}
