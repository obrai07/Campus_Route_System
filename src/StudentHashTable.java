/**
 * StudentHashTable.java
 * MEMBER 3 RESPONSIBILITY (part 2): Hashing implementation for efficient
 * student ID searching. Uses separate chaining to handle collisions.
 */
public class StudentHashTable {

    private static class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node[] buckets;
    private int capacity;
    private int size;

    public StudentHashTable() {
        this(16);
    }

    public StudentHashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    /** Simple hash function based on the Student ID string. */
    private int hash(String studentId) {
        int hashCode = Math.abs(studentId.toUpperCase().hashCode());
        return hashCode % capacity;
    }

    /** Insert (or overwrite) a student in the hash table. */
    public void put(Student student) {
        int index = hash(student.getStudentId());
        Node current = buckets[index];
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(student.getStudentId())) {
                current.data = student; // overwrite existing
                return;
            }
            current = current.next;
        }
        Node newNode = new Node(student);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        // grow the table if load factor gets too high
        if ((double) size / capacity > 0.75) resize();
    }

    /** O(1)-average search for a student by ID via hashing. */
    public Student get(String studentId) {
        int index = hash(studentId);
        Node current = buckets[index];
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // not found
    }

    /** Remove a student from the hash table by ID. */
    public boolean remove(String studentId) {
        int index = hash(studentId);
        Node current = buckets[index];
        Node prev = null;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                if (prev == null) buckets[index] = current.next;
                else prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    private void resize() {
        Node[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new Node[capacity];
        size = 0;
        for (Node head : oldBuckets) {
            Node current = head;
            while (current != null) {
                put(current.data);
                current = current.next;
            }
        }
    }

    public int size() { return size; }
}
