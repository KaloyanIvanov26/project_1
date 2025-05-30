# Hotel Reservation System

This project is a simple Hotel Reservation System implemented in Java with a Swing-based graphical user interface. It allows users to manage hotel rooms, guests, and reservations efficiently.

## Features

- **Room Management:** Add, delete, and view hotel rooms.
- **Reservation Management:** Add, delete, and search for reservations by guest name or room number.
- **Guest Management:** Track guests and their reservations.
- **Data Persistence:** Save and load room and reservation data to/from `data.txt`.
- **Room Tree:** View rooms in a binary search tree structure (console output).
- **Sorting:** Sort reservations by check-in date.
- **Custom Data Structures:** Uses custom implementations of dynamic arrays and binary search trees.

## Project Structure

```
project_1/
│
├── bin/                        # Compiled .class files
├── data.txt                    # Data file for rooms and reservations
├── src/
│   └── hotel_project/
│       ├── DynamicArray.java
│       ├── GenericBST.java
│       ├── Guest.java
│       ├── HotelReservationSystem.java
│       ├── Reservation.java
│       ├── ReservationManager.java
│       └── Room.java
└── README.md
```

## How to Run

1. **Compile the project:**
   - Make sure you have Java installed (JDK 23 recommended).
   - Compile all `.java` files in the `src/hotel_project` directory:
     ```sh
     javac -d bin src/hotel_project/*.java
     ```

2. **Run the application:**
   - From the project root, run:
     ```sh
     java -cp bin hotel_project.HotelReservationSystem
     ```

## Usage

- **Add Room:** Enter room details and click "Add Room".
- **Add Reservation:** Select a room, enter guest name and dates, then click "Add Reservation".
- **Delete Reservation/Room:** Use the search box or select a room, then click the appropriate button.
- **Search:** Enter a guest name or room number to search.
- **Save/Load Data:** Use the "Save Data" and "Load Data" buttons to persist or restore data.
- **Print Room Tree:** Outputs the room BST to the console.

## Data File Format

- Rooms and reservations are saved in `data.txt` in CSV format.

## Requirements

- Java 8 or higher (JDK 23 recommended)
- Visual Studio Code was used to develop this project

## Authors

- Kaloyan Ivanov

---