package astraea.task;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the list of Tasks tracked by Astraea.
 * Can hold up to 100 Tasks.
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>(100);
    }

    /**
     * Adds the given Task to the end of this list.
     *
     * @param task Task to be added to list.
     */
    public void add(Task task) {
        list.add(task);
    }

    /**
     * Removes the Task corresponding to the given index from this list.
     *
     * @param index Index of Task to be removed.
     * @return Task removed from list.
     */
    public Task remove(int index) {
        return list.remove(index);
    }

    /**
     * Returns corresponding Task in this list at given index.
     *
     * @param index Index of Task to retrieve.
     * @return Task at index in list.
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Returns the number of Tasks in this list.
     *
     * @return Number of Tasks in list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns <code>true</code> if list contains no Tasks.
     *
     * @return <code>true</code> if list contains no Tasks.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    @Override
    public Iterator<Task> iterator() {
        return list.iterator();
    }
}
