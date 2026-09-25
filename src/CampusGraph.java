import java.util.*;

/**
 * CampusGraph.java
 * MEMBER 4 RESPONSIBILITY: Graph implementation, campus locations, connections,
 * and BFS/DFS traversal. COMPULSORY component - cannot be omitted.
 *
 * Represented using an adjacency list: each location maps to a list of
 * directly connected (neighbouring) locations. Treated as an undirected graph
 * (a road connects both ways) - adjust to directed if your campus needs it.
 */
public class CampusGraph {

    private final Map<String, List<String>> adjacencyList; // locationName -> list of neighbour names

    public CampusGraph() {
        adjacencyList = new LinkedHashMap<>(); // preserves insertion order for nicer display
    }

    /** Add a new campus location (vertex). Returns false if it already exists. */
    public boolean addLocation(String locationName) {
        if (adjacencyList.containsKey(locationName)) return false; // duplicate location
        adjacencyList.put(locationName, new ArrayList<>());
        return true;
    }

    /** Remove a campus location and all connections/roads involving it. */
    public boolean removeLocation(String locationName) {
        if (!adjacencyList.containsKey(locationName)) return false; // missing location
        adjacencyList.remove(locationName);
        // also remove this location from every other location's neighbour list
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(locationName);
        }
        return true;
    }

    /** Add an undirected connection/road between two existing locations. */
    public boolean addConnection(String locationA, String locationB) {
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false; // one or both locations don't exist
        }
        if (adjacencyList.get(locationA).contains(locationB)) {
            return false; // connection already exists
        }
        adjacencyList.get(locationA).add(locationB);
        adjacencyList.get(locationB).add(locationA);
        return true;
    }

    /** Remove the connection/road between two locations. */
    public boolean removeConnection(String locationA, String locationB) {
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false; // unavailable connection: locations don't exist
        }
        boolean removedA = adjacencyList.get(locationA).remove(locationB);
        boolean removedB = adjacencyList.get(locationB).remove(locationA);
        return removedA || removedB;
    }

    /** Display every location and its direct connections. */
    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations added yet.");
            return;
        }
        System.out.println("---- Campus Network (Adjacency List) ----");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    /** Breadth-First Search traversal starting from a given location. */
    public List<String> bfs(String startLocation) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(startLocation)) return visitedOrder; // not found

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startLocation);
        visited.add(startLocation);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitedOrder.add(current);
            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return visitedOrder;
    }

    /** Depth-First Search traversal starting from a given location. */
    public List<String> dfs(String startLocation) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(startLocation)) return visitedOrder; // not found
        Set<String> visited = new HashSet<>();
        dfsHelper(startLocation, visited, visitedOrder);
        return visitedOrder;
    }

    private void dfsHelper(String current, Set<String> visited, List<String> visitedOrder) {
        visited.add(current);
        visitedOrder.add(current);
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, visited, visitedOrder);
            }
        }
    }

    public boolean hasLocation(String locationName) {
        return adjacencyList.containsKey(locationName);
    }

    public Set<String> getAllLocations() {
        return adjacencyList.keySet();
    }

    /** Get the list of directly connected neighbours for a location (used for persistence/reporting). */
    public List<String> getNeighbours(String locationName) {
        return adjacencyList.getOrDefault(locationName, new ArrayList<>());
    }
}
