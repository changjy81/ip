package astraea.task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> list;
    private final int MAX_CAPACITY = 100;

    public TaskList() {
        this.list = new ArrayList<>(MAX_CAPACITY);
    }

    public void add(Task task) {
        list.add(task);
    }

    public Task remove(int index) {
        return list.remove(index);
    }

    public Task get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return list.iterator();
    }
}
