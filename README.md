# University Student Record and Campus Route Management System

CIT300 Data Structures and Algorithms — Practical Assignment 1

## How to compile and run

From the project root (in Eclipse: just import as a Java project and run `Main.java`):

    javac -d bin src/*.java
    cd bin
    java Main

## File-to-member mapping

| File | Member | Data structure |
|---|---|---|
| `Student.java` | Member 1 | Student record model |
| `StudentLinkedList.java` | Member 1 | Linked list — add/update/delete/display records |
| `ActionStack.java` | Member 2 | Stack — recent actions / undo history |
| `ServiceQueue.java` | Member 2 | Queue — service requests, FIFO |
| `StudentBST.java` | Member 3 | BST — records sorted/searchable by Student ID |
| `StudentHashTable.java` | Member 3 | Hash table (separate chaining) — fast ID search |
| `Location.java` | Member 4 | Graph vertex model |
| `CampusGraph.java` | Member 4 | Graph (adjacency list) — locations, connections, BFS/DFS |
| `FileManager.java` | Member 4 | Save/load students and campus data to text files |
| `Main.java` | All members | Menu-driven console interface tying everything together |

## What's implemented

- Add / Update / Delete / Display student records (linked list)
- Duplicate ID, missing record, and invalid marks handling
- Recent-action history (stack)
- Service request queue (add + process next)
- Sorted display and search via BST
- O(1)-average search via hash table (separate chaining, auto-resizes)
- Campus graph: add/remove locations, add/remove connections, display network
- BFS and DFS traversal from any starting location
- Data persistence: student records and campus data are saved to `students.txt` / `locations.txt` / `connections.txt` on exit and automatically reloaded next time the app starts
- Summary report (menu option 16): total students, average/top/bottom marks, pending requests, logged actions, and campus location count
- Full 17-option menu matching the assignment spec, with input validation throughout

## Group Members

| Name | Student ID | Responsibility |
|---|---|---|
| M.A.M. Affan | 23DA2-1177 | Linked list implementation and student-record management (`Student.java`, `StudentLinkedList.java`) |
| M.M.M. Mashdi | 23DA2-1018 | Stack and queue implementation and related operations (`ActionStack.java`, `ServiceQueue.java`) |
| R.M. Riskan | 23DA2-0804 | BST/AVL tree implementation and hashing/search functionality (`StudentBST.java`, `StudentHashTable.java`) |
| S.I.M. Shimak | 23DA2-0616 | Graph implementation, campus locations, connections, BFS/DFS traversal, data persistence, and system integration (`Location.java`, `CampusGraph.java`, `FileManager.java`, `Main.java`) |

**All members:** Integration, validation, testing, debugging, documentation, and GitHub collaboration (branches, commits, pull requests).
