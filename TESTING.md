# Testing — University Student Record and Campus Route Management System

Manual test cases covering normal operations and required edge cases (duplicate
IDs/locations, missing records, invalid marks, unavailable connections) as
required by the assignment brief.

## Student Records (Member 1 — M.A.M. Affan)

| # | Test Case | Steps | Expected Result | Pass/Fail |
|---|---|---|---|---|
| 1 | Add student | Menu 1 → enter new ID, name, programme, marks | Student added successfully | |
| 2 | Duplicate ID | Menu 1 → enter an ID that already exists | Error: duplicate ID, not added | |
| 3 | Update existing student | Menu 2 → enter valid ID → change name/marks | Student updated successfully | |
| 4 | Update non-existent student | Menu 2 → enter an ID that doesn't exist | Error: student not found | |
| 5 | Delete existing student | Menu 3 → enter valid ID | Student deleted successfully | |
| 6 | Delete non-existent student | Menu 3 → enter an ID already deleted / never added | Error: student not found | |
| 7 | Invalid marks | Menu 1 → enter marks like -5 or 150 | Error: marks must be 0-100 | |
| 8 | Display all | Menu 4 | All current records listed correctly | |

## Stack & Queue (Member 2 — M.M.M. Mashdi)

| # | Test Case | Steps | Expected Result | Pass/Fail |
|---|---|---|---|---|
| 9 | Add service request | Menu 5 → enter ID + request description | Request added to queue | |
| 10 | Process request | Menu 6 | Oldest request processed (FIFO order) | |
| 11 | Process on empty queue | Menu 6 with no pending requests | Message: no pending requests, no crash | |
| 12 | Display recent actions | Menu 7 after a few operations | Most recent action shown first | |

## BST & Hashing (Member 3 — R.M. Riskan)

| # | Test Case | Steps | Expected Result | Pass/Fail |
|---|---|---|---|---|
| 13 | Search existing ID | Menu 9 → enter valid ID | Student details returned | |
| 14 | Search non-existent ID | Menu 9 → enter invalid/unused ID | Error: not found, no crash | |
| 15 | Display sorted (BST) | Menu 8 | Students listed in sorted order by ID | |

## Campus Graph (Member 4 — S.I.M. Shimak)

| # | Test Case | Steps | Expected Result | Pass/Fail |
|---|---|---|---|---|
| 16 | Add location | Menu 10 → enter new location name | Location added | |
| 17 | Duplicate location | Menu 10 → enter an existing location name | Error: location already exists | |
| 18 | Add connection | Menu 12 → enter two existing locations | Connection added both ways | |
| 19 | Connect to missing location | Menu 12 → one location doesn't exist | Error: location not found | |
| 20 | Remove connection | Menu 13 → enter two connected locations | Connection removed | |
| 21 | Remove non-existent connection | Menu 13 → same pair again, or unconnected pair | Error: connection unavailable | |
| 22 | Display connections | Menu 14 | Full adjacency list displayed correctly | |
| 23 | BFS traversal | Menu 15 → valid start location → BFS | Locations visited level by level | |
| 24 | DFS traversal | Menu 15 → valid start location → DFS | Locations visited depth-first | |
| 25 | Traverse from missing location | Menu 15 → non-existent location | Error: location not found | |

## Persistence & Reporting (System-wide)

| # | Test Case | Steps | Expected Result | Pass/Fail |
|---|---|---|---|---|
| 26 | Save & reload | Add data → Menu 17 (Save & Exit) → relaunch app | Previously saved data loads automatically | |
| 27 | Summary report | Menu 16 after adding several students | Correct total, average, top/bottom performer shown | |

