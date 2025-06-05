package hotel_project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class ReservationManager {
    private DynamicArray<Reservation> reservations;
    private DynamicArray<Room> rooms;
    private DynamicArray<Guest> guests;
    private GenericBST<Room> roomTree;

    public ReservationManager() {
        reservations = new DynamicArray<>();
        rooms = new DynamicArray<>();
        guests = new DynamicArray<>();
        roomTree = new GenericBST<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        roomTree.insert(room);
    }

public void addGuest(Guest guest) {
        guests.add(guest);
    }

public boolean deleteRoom(int roomNumber) {
    for (int i = 0; i < rooms.size(); i++) {
        Room room = rooms.get(i);
        if (room.getRoomNumber() == roomNumber) {
            if (!room.isAvailable()) {
                return false; 
            }
            rooms.remove(i);
            roomTree.delete(room);
            return true;
        }
    }
    return false;
}

    public void addReservation(Reservation reservation) throws Exception {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.getGuest().getName().equalsIgnoreCase(reservation.getGuest().getName())) {
                if (datesOverlap(r.getCheckInDate(), r.getCheckOutDate(),
                                 reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                    throw new Exception("Duplicate reservation – overlapping dates for the guest!");
                }
            }
        }
        reservations.add(reservation);
        reservation.getRoom().setAvailable(false);
        reservation.getGuest().setReservation(reservation);
    }

    private boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }

    public boolean updateReservation(String guestName, Reservation newReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.getGuest().getName().equalsIgnoreCase(guestName) ||
                r.getRoom().getRoomNumber() == newReservation.getRoom().getRoomNumber()) {
                reservations.remove(i);
                reservations.add(newReservation);
                return true;
            }
        }
        return false;
    }

    public boolean deleteReservation(String guestName) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.getGuest().getName().equalsIgnoreCase(guestName)) {
                r.getRoom().setAvailable(true);
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }

    public void sortReservationsByCheckInDate() {
        for (int i = 1; i < reservations.size(); i++) {
            Reservation key = reservations.get(i);
            int j = i - 1;
            while (j >= 0 && reservations.get(j).getCheckInDate().after(key.getCheckInDate())) {
                Reservation temp = reservations.get(j + 1);
                reservations.remove(j + 1);
                reservations.add(j, temp);
                j--;
            }
        }
    }

    public DynamicArray<Room> getRooms() {
        return rooms;
    }

    public DynamicArray<Reservation> getReservations() {
        return reservations;
    }

    public DynamicArray<Guest> getGuests() {
        return guests;
    }

    public GenericBST<Room> getRoomTree() {
        return roomTree;
    }
    
    public Room searchRoomByNumber(int roomNumber) {
    for (Room room : getRooms()) {
        if (room.getRoomNumber() == roomNumber) {
            return room;
        }
    }
    return null;
    }

    public Reservation searchByGuestName(String guestName) {
    for (Reservation reservation : reservations) {
        if (reservation.getGuest().getName().equalsIgnoreCase(guestName)) {
            return reservation;
        }
    }
    return null;
    }

    public void saveData(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (Room room : getRooms()) {
            writer.write("ROOM," + room.getRoomNumber() + "," + room.getType() + "," + room.getPrice() + "," + room.isAvailable() + "\n");
        }
        for (Reservation reservation : getReservations()) {
            writer.write("RESERVATION," + reservation.getRoom().getRoomNumber() + "," + reservation.getGuest().getName() + "," +
                    reservation.getCheckInDate() + "," + reservation.getCheckOutDate() + "\n");
        }
        writer.close();
    }


public void loadData(String filename) throws Exception {
    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length > 0) {
            if ("ROOM".equals(parts[0])) {
                int roomNumber = Integer.parseInt(parts[1]);
                String type = parts[2];
                double price = Double.parseDouble(parts[3]);
                addRoom(new Room(roomNumber, type, price));
            }
        }
    }
    reader.close();
}
}


