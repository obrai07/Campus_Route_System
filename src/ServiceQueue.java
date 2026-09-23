/**
 * ServiceQueue.java
 * MEMBER 2 RESPONSIBILITY (part 2): Queue implementation for student service requests.
 *
 * A simple linked FIFO queue. Requests are processed in the exact order
 * they arrive (first-come, first-served).
 */
public class ServiceQueue {

    private static class Node {
        String request; // e.g. "STU001 - Transcript Request"
        Node next;
        Node(String request) { this.request = request; }
    }

    private Node front;
    private Node rear;
    private int size;

    public ServiceQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /** Add a new service request to the back of the queue. */
    public void enqueue(String request) {
        Node node = new Node(request);
        if (rear == null) {
            front = node;
            rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /** Remove and return the next request to be served. Returns null if empty. */
    public String dequeue() {
        if (isEmpty()) return null;
        String request = front.request;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return request;
    }

    /** Look at the next request without removing it. */
    public String peekFront() {
        return isEmpty() ? null : front.request;
    }

    public boolean isEmpty() { return front == null; }
    public int size() { return size; }

    /** Display all pending requests, in service order. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        System.out.println("---- Pending Service Requests (Queue, arrival order) ----");
        Node current = front;
        int count = 1;
        while (current != null) {
            System.out.println(count++ + ". " + current.request);
            current = current.next;
        }
    }
}
