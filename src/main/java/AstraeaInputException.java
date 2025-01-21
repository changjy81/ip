public class AstraeaInputException extends Exception {
    private String type;
    static String separator = "\t____________________________________________________________";

    public AstraeaInputException(String type) {
        this.type = type;
    }

    public void print() {
        System.out.println(separator);
        switch (type) {
            case "todo_noName":
                System.out.println("\t You should probably give an actual name to your task, hm?");
                System.out.println("\t Usage: todo [name]");
                break;
            case "deadline_noName":
                System.out.println("\t You should probably give an actual name to your task, hm?");
                System.out.println("\t Usage: deadline [name] /by [time]");
                break;
            case "deadline_noTime":
                System.out.println("\t I can't tell when you need this done by. Did you forget the divider?");
                System.out.println("\t Usage: deadline [name] /by [time]");
                break;
            case "event_noName":
                System.out.println("\t You should probably give an actual name to your task, hm?");
                System.out.println("\t Usage: event [name] /from [time] /to [time]");
                break;
            case "event_noStart":
                System.out.println("\t I can't tell when this starts. Did you forget the divider?");
                System.out.println("\t Usage: event [name] /from [time] /to [time]");
                break;
            case "event_noEnd":
                System.out.println("\t I can't tell when this ends. Did you forget the divider?");
                System.out.println("\t Usage: event [name] /from [time] /to [time]");
                break;
            case "mark":
                System.out.println("\t Give me just one number for the index of the task.");
                System.out.println("\t Usage: mark [index]");
                break;
            case "unmark":
                System.out.println("\t Give me just one number for the index of the task.");
                System.out.println("\t Usage: unmark [index]");
                break;
            default:
                System.out.println("\t What on earth are you blabbering about?");
                System.out.println("\t Your first word isn't anything I can process.");
        }
        System.out.println(separator);
    }
}
