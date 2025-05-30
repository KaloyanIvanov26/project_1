package hotel_project;

public class Room implements Comparable<Room> {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isAvailable; 

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true; 
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public int compareTo(Room other) {
        int typeCompare = this.type.compareTo(other.getType());
        if (typeCompare == 0) {
            return Double.compare(this.price, other.getPrice());
        }
        return typeCompare;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - Price: " + price +
                ", Status: " + (isAvailable ? "Available"  : "Occupied");
    }
}
