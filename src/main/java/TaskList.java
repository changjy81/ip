import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list = new ArrayList<>(100);

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public void add(Task task) {
        list.add(task);
    }

    public void remove(Task task) {
        list.remove(task);
    }
}
