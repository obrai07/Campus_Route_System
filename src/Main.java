import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 * ALL MEMBERS: Integration point. Wires together the linked list (M1),
 * stack + queue (M2), BST + hashing (M3), and graph (M4) behind one
 * menu-driven console interface, per the assignment's suggested menu.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Shared data structures across the whole app
    private static final StudentLinkedList linkedList = new StudentLinkedList();
    private static final ActionStack actionStack = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST bst = new StudentBST();
    private static final StudentHashTable hashTable = new StudentHashTable();
    private static final CampusGraph graph = new CampusGraph();

    public static void main(String[] args) {
        // NEW: load any previously saved data so the system doesn't start empty every run
        FileManager.loadStudents(linkedList, bst, hashTable);
        FileManager.loadCampus(graph);
        if (linkedList.size() > 0 || !graph.getAllLocations().isEmpty()) {
            System.out.println("Loaded " + linkedList.size() + " student record(s) and " +
                    graph.getAllLocations().size() + " campus location(s) from previous session.\n");
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addStudentRecord(); break;
                case 2: updateStudentRecord(); break;
                case 3: deleteStudentRecord(); break;
                case 4: linkedList.displayAll(); break;
                case 5: addServiceRequest(); break;
                case 6: processNextServiceRequest(); break;
                case 7: actionStack.displayAll(); break;
                case 8: bst.displayInOrder(); break;
                case 9: searchStudentByHashing(); break;
                case 10: addCampusLocation(); break;
                case 11: removeCampusLocation(); break;
                case 12: addCampusConnection(); break;
                case 13: removeCampusConnection(); break;
                case 14: graph.displayConnections(); break;
                case 15: traverseCampus(); break;
                case 16: generateSummaryReport(); break;
                case 17:
                    running = false;
                    // NEW: persist everything to disk before closing
                    FileManager.saveStudents(linkedList);
                    FileManager.saveCampus(graph);
                    System.out.println("All data saved. Goodbye!");
                    break;
                default: System.out.println("Invalid choice. Please select 1-17.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== University Student Record & Campus Route Management System =====");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST/AVL");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Generate Summary Report");
        System.out.println("17. Save & Exit");
    }

    // ---------- NEW: Summary report (aggregate stats across records + campus) ----------

    private static void generateSummaryReport() {
        System.out.println("========== SYSTEM SUMMARY REPORT ==========");
        Student[] students = linkedList.toArray();
        System.out.println("Total student records : " + students.length);

        if (students.length > 0) {
            double total = 0;
            Student top = students[0];
            Student bottom = students[0];
            for (Student s : students) {
                total += s.getMarks();
                if (s.getMarks() > top.getMarks()) top = s;
                if (s.getMarks() < bottom.getMarks()) bottom = s;
            }
            System.out.printf("Average marks          : %.2f%n", total / students.length);
            System.out.println("Top performer          : " + top.getName() + " (" + top.getStudentId() + ", " + top.getMarks() + ")");
            System.out.println("Lowest performer        : " + bottom.getName() + " (" + bottom.getStudentId() + ", " + bottom.getMarks() + ")");
        }

        System.out.println("Pending service requests: " + serviceQueue.size());
        System.out.println("Actions logged          : " + actionStack.size());
        System.out.println("Campus locations        : " + graph.getAllLocations().size());
        System.out.println("=============================================");
    }

    // ---------- Student record operations (M1 linked list + M3 BST/hash kept in sync) ----------

    private static void addStudentRecord() {
        String id = readLine("Student ID: ");
        String name = readLine("Name: ");
        String programme = readLine("Programme: ");
        double marks = readDouble("Marks (0-100): ");

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks. Must be between 0 and 100.");
            return;
        }
        Student student = new Student(id, name, programme, marks);
        boolean added = linkedList.addStudent(student);
        if (!added) {
            System.out.println("Error: Student ID '" + id + "' already exists (duplicate).");
            return;
        }
        bst.insert(student);
        hashTable.put(student);
        actionStack.push("ADD: " + id + " - " + name);
        System.out.println("Student added successfully.");
    }

    private static void updateStudentRecord() {
        String id = readLine("Student ID to update: ");
        if (linkedList.findStudent(id) == null) {
            System.out.println("Error: No student found with ID '" + id + "'.");
            return;
        }
        String name = readLine("New Name (blank to keep current): ");
        String programme = readLine("New Programme (blank to keep current): ");
        double marks = readDouble("New Marks (-1 to keep current): ");

        linkedList.updateStudent(id, name, programme, marks);
        // keep hash table's copy consistent (BST stores a reference to the same object, so it's already updated)
        hashTable.put(linkedList.findStudent(id));
        actionStack.push("UPDATE: " + id);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudentRecord() {
        String id = readLine("Student ID to delete: ");
        Student removed = linkedList.deleteStudent(id);
        if (removed == null) {
            System.out.println("Error: No student found with ID '" + id + "'.");
            return;
        }
        bst.delete(id);
        hashTable.remove(id);
        actionStack.push("DELETE: " + id + " - " + removed.getName());
        System.out.println("Student deleted successfully.");
    }

    // ---------- Queue operations (M2) ----------

    private static void addServiceRequest() {
        String id = readLine("Student ID: ");
        String requestType = readLine("Request description (e.g. Transcript Request): ");
        serviceQueue.enqueue(id + " - " + requestType);
        actionStack.push("QUEUE: added request for " + id);
        System.out.println("Service request added to queue.");
    }

    private static void processNextServiceRequest() {
        String next = serviceQueue.dequeue();
        if (next == null) {
            System.out.println("No pending service requests.");
            return;
        }
        actionStack.push("PROCESSED: " + next);
        System.out.println("Processed request: " + next);
    }

    // ---------- Hashing search (M3) ----------

    private static void searchStudentByHashing() {
        String id = readLine("Student ID to search: ");
        Student result = hashTable.get(id);
        if (result == null) {
            System.out.println("No student found with ID '" + id + "'.");
        } else {
            System.out.println("Found: " + result);
        }
    }

    // ---------- Graph operations (M4) ----------

    private static void addCampusLocation() {
        String name = readLine("Location name: ");
        if (graph.addLocation(name)) {
            actionStack.push("ADD LOCATION: " + name);
            System.out.println("Location added.");
        } else {
            System.out.println("Error: Location '" + name + "' already exists.");
        }
    }

    private static void removeCampusLocation() {
        String name = readLine("Location name to remove: ");
        if (graph.removeLocation(name)) {
            actionStack.push("REMOVE LOCATION: " + name);
            System.out.println("Location removed.");
        } else {
            System.out.println("Error: Location '" + name + "' not found.");
        }
    }

    private static void addCampusConnection() {
        String a = readLine("First location: ");
        String b = readLine("Second location: ");
        if (graph.addConnection(a, b)) {
            actionStack.push("ADD CONNECTION: " + a + " <-> " + b);
            System.out.println("Connection added.");
        } else {
            System.out.println("Error: could not add connection (missing location or duplicate).");
        }
    }

    private static void removeCampusConnection() {
        String a = readLine("First location: ");
        String b = readLine("Second location: ");
        if (graph.removeConnection(a, b)) {
            actionStack.push("REMOVE CONNECTION: " + a + " <-> " + b);
            System.out.println("Connection removed.");
        } else {
            System.out.println("Error: connection unavailable (locations or road don't exist).");
        }
    }

    private static void traverseCampus() {
        String start = readLine("Start location: ");
        if (!graph.hasLocation(start)) {
            System.out.println("Error: Location '" + start + "' not found.");
            return;
        }
        String mode = readLine("Traversal type (BFS/DFS): ");
        List<String> order;
        if (mode.equalsIgnoreCase("DFS")) {
            order = graph.dfs(start);
            System.out.println("DFS order: " + order);
        } else {
            order = graph.bfs(start);
            System.out.println("BFS order: " + order);
        }
    }

    // ---------- Input helpers with basic validation ----------

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return -1; // sentinel for "keep current" in update flow
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
}
