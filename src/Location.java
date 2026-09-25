/**
 * Location.java
 * MEMBER 4 RESPONSIBILITY: represents a single campus location (a vertex in the graph).
 */
public class Location {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location)) return false;
        return name.equalsIgnoreCase(((Location) obj).name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
