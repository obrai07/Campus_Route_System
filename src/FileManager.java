import java.io.*;
import java.util.*;

/**
 * FileManager.java
 * NEW ADDITION: Data persistence layer.
 *
 * Without this, all student records and campus data are lost the moment the
 * program exits, which looks unfinished in a demo. This class saves/loads
 * everything to plain text files (CSV-style) so the system's state survives
 * between runs — students.txt and campus.txt sit alongside the compiled app.
 */
public class FileManager {

    private static final String STUDENTS_FILE = "students.txt";
    private static final String LOCATIONS_FILE = "locations.txt";
    private static final String CONNECTIONS_FILE = "connections.txt";

    /** Save every student record currently in the linked list to disk. */
    public static void saveStudents(StudentLinkedList list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : list.toArray()) {
                writer.println(s.getStudentId() + "," + s.getName() + "," +
                        s.getProgramme() + "," + s.getMarks());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save student data (" + e.getMessage() + ")");
        }
    }

    /** Load students from disk into all three structures (linked list, BST, hash table). */
    public static void loadStudents(StudentLinkedList list, StudentBST bst, StudentHashTable hashTable) {
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) return; // first run — nothing to load yet

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", 4);
                if (parts.length != 4) continue; // skip malformed rows instead of crashing
                try {
                    Student s = new Student(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                    list.addStudent(s);
                    bst.insert(s);
                    hashTable.put(s);
                } catch (NumberFormatException ignored) {
                    // skip a corrupted row rather than failing the whole load
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not load student data (" + e.getMessage() + ")");
        }
    }

    /** Save campus locations and their connections to disk. */
    public static void saveCampus(CampusGraph graph) {
        try (PrintWriter locWriter = new PrintWriter(new FileWriter(LOCATIONS_FILE));
             PrintWriter conWriter = new PrintWriter(new FileWriter(CONNECTIONS_FILE))) {

            Set<String> written = new HashSet<>();
            for (String location : graph.getAllLocations()) {
                locWriter.println(location);
                for (String neighbour : graph.getNeighbours(location)) {
                    // write each undirected edge only once (A-B, not also B-A)
                    String key = location.compareTo(neighbour) < 0
                            ? location + "|" + neighbour
                            : neighbour + "|" + location;
                    if (written.add(key)) {
                        conWriter.println(location + "," + neighbour);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save campus data (" + e.getMessage() + ")");
        }
    }

    /** Load campus locations and connections from disk into the graph. */
    public static void loadCampus(CampusGraph graph) {
        File locFile = new File(LOCATIONS_FILE);
        if (locFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(locFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank()) graph.addLocation(line.trim());
                }
            } catch (IOException e) {
                System.out.println("Warning: could not load location data (" + e.getMessage() + ")");
            }
        }

        File conFile = new File(CONNECTIONS_FILE);
        if (conFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(conFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) continue;
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) graph.addConnection(parts[0].trim(), parts[1].trim());
                }
            } catch (IOException e) {
                System.out.println("Warning: could not load connection data (" + e.getMessage() + ")");
            }
        }
    }
}
