/**
 * StudentBST.java
 * MEMBER 3 RESPONSIBILITY (part 1): BST/AVL tree implementation.
 *
 * A Binary Search Tree keyed on Student ID, used to organize records and
 * display them in sorted order (in-order traversal). Basic BST - you can
 * extend this to a self-balancing AVL tree if you want extra credit /
 * to demonstrate rotations, but a plain BST satisfies the requirement.
 */
public class StudentBST {

    private static class TreeNode {
        Student data;
        TreeNode left, right;
        TreeNode(Student data) { this.data = data; }
    }

    private TreeNode root;
    private int size;

    /** Insert a student into the tree, keyed by Student ID. */
    public boolean insert(Student student) {
        if (search(student.getStudentId()) != null) return false; // duplicate
        root = insertRec(root, student);
        size++;
        return true;
    }

    private TreeNode insertRec(TreeNode node, Student student) {
        if (node == null) return new TreeNode(student);
        int cmp = student.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) node.left = insertRec(node.left, student);
        else if (cmp > 0) node.right = insertRec(node.right, student);
        return node;
    }

    /** Search for a student by ID. Returns null if not found. */
    public Student search(String studentId) {
        TreeNode node = root;
        while (node != null) {
            int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
            if (cmp == 0) return node.data;
            node = (cmp < 0) ? node.left : node.right;
        }
        return null;
    }

    /** Remove a student by ID from the tree. */
    public boolean delete(String studentId) {
        if (search(studentId) == null) return false;
        root = deleteRec(root, studentId);
        size--;
        return true;
    }

    private TreeNode deleteRec(TreeNode node, String studentId) {
        if (node == null) return null;
        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            TreeNode successor = node.right;
            while (successor.left != null) successor = successor.left;
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    /** Display all students in sorted order by Student ID (in-order traversal). */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("No student records in tree.");
            return;
        }
        System.out.println("---- Students Sorted by ID (BST In-order) ----");
        int[] count = {1};
        inOrderRec(root, count);
    }

    private void inOrderRec(TreeNode node, int[] count) {
        if (node == null) return;
        inOrderRec(node.left, count);
        System.out.println(count[0]++ + ". " + node.data);
        inOrderRec(node.right, count);
    }

    public void clear() { root = null; size = 0; }
    public int size() { return size; }
}
