package hotel_project;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
    private Room room;
    private Guest guest;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Room room, Guest guest, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckInDate(Date date) {
        checkInDate = date;
    }

    public void setCheckOutDate(Date date) {
        checkOutDate = date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Reservation for room " + room.getRoomNumber() +
               " from " + sdf.format(checkInDate) +
               " to " + sdf.format(checkOutDate);
    }
}
