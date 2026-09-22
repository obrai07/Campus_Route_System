/**
 * StudentLinkedList.java
 * MEMBER 1 RESPONSIBILITY: Linked list implementation and student-record management.
 *
 * A singly linked list used as the primary storage for all student records.
 * Covers: Add, Update, Delete, Display, Find (used by other components too).
 */
public class StudentLinkedList {

    // Internal node class
    private static class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node head;
    private int size;

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    /** Add a new student record. Returns false if the ID already exists (duplicate check). */
    public boolean addStudent(Student student) {
        if (findStudent(student.getStudentId()) != null) {
            return false; // duplicate ID
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        size++;
        return true;
    }

    /** Find a student by ID. Returns null if not found (missing-record case). */
    public Student findStudent(String studentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Update an existing student's name/programme/marks. Returns false if not found. */
    public boolean updateStudent(String studentId, String name, String programme, double marks) {
        Student s = findStudent(studentId);
        if (s == null) return false;
        if (name != null && !name.isEmpty()) s.setName(name);
        if (programme != null && !programme.isEmpty()) s.setProgramme(programme);
        if (marks >= 0) s.setMarks(marks);
        return true;
    }

    /** Delete a student by ID. Returns the removed Student, or null if not found. */
    public Student deleteStudent(String studentId) {
        Node current = head;
        Node prev = null;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.data;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /** Display all records by traversing the linked list. */
    public void displayAll() {
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("---- All Student Records (Linked List) ----");
        Node current = head;
        int count = 1;
        while (current != null) {
            System.out.println(count++ + ". " + current.data);
            current = current.next;
        }
    }

    /** Returns all students as an array snapshot - used by BST/Hash/other components to (re)build indexes. */
    public Student[] toArray() {
        Student[] arr = new Student[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}
