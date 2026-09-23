/**
 * ActionStack.java
 * MEMBER 2 RESPONSIBILITY (part 1): Stack implementation for recent actions / history.
 *
 * A simple array-based (or linked) LIFO stack that records a text description
 * of every action performed on the system (add/update/delete/etc.), so the
 * most recent action is always on top. Used for the "Display Recent Actions" menu item.
 */
public class ActionStack {

    private static class Node {
        String action;
        Node next;
        Node(String action) { this.action = action; }
    }

    private Node top;
    private int size;
    private final int MAX_HISTORY; // optional cap so the log doesn't grow forever

    public ActionStack() {
        this(100);
    }

    public ActionStack(int maxHistory) {
        top = null;
        size = 0;
        this.MAX_HISTORY = maxHistory;
    }

    /** Push a new action description onto the stack. */
    public void push(String action) {
        Node node = new Node(action);
        node.next = top;
        top = node;
        size++;
        // trim oldest entries if we exceed the cap
        if (size > MAX_HISTORY) {
            trimOldest();
        }
    }

    private void trimOldest() {
        if (top == null) return;
        Node current = top;
        while (current.next != null && current.next.next != null) {
            current = current.next;
        }
        current.next = null;
        size--;
    }

    /** Pop (undo) the most recent action. Returns null if stack is empty. */
    public String pop() {
        if (isEmpty()) return null;
        String action = top.action;
        top = top.next;
        size--;
        return action;
    }

    /** Peek at the most recent action without removing it. */
    public String peek() {
        return isEmpty() ? null : top.action;
    }

    public boolean isEmpty() { return top == null; }
    public int size() { return size; }

    /** Display all recorded actions, most recent first. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("---- Recent Actions (Stack, most recent first) ----");
        Node current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count++ + ". " + current.action);
            current = current.next;
        }
    }
}
