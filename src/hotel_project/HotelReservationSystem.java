package hotel_project;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HotelReservationSystem extends JFrame {
    private ReservationManager reservationManager;
    private JTable roomTable;
    private DefaultTableModel roomTableModel;
    private JTextField txtRoomNumber, txtType, txtPrice, txtGuestName, txtCheckIn, txtCheckOut, txtSearch;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HotelReservationSystem() {
        reservationManager = new ReservationManager();
        initGUI();
        loadSampleData(); 
    }

    private void initGUI() {
        setTitle("Hotel Reservation System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topPanel);

        JPanel roomInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roomInputPanel.add(new JLabel("Room Number:"));
        txtRoomNumber = new JTextField(5);
        roomInputPanel.add(txtRoomNumber);
        roomInputPanel.add(new JLabel("Type:"));
        txtType = new JTextField(10);
        roomInputPanel.add(txtType);
        roomInputPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField(8);
        roomInputPanel.add(txtPrice);
        JButton btnAddRoom = new JButton("Add Room");
        btnAddRoom.addActionListener(e -> addRoomAction());
        roomInputPanel.add(btnAddRoom);
        topPanel.add(roomInputPanel);

        JPanel reservationInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reservationInputPanel.add(new JLabel("Guest Name:"));
        txtGuestName = new JTextField(10);
        reservationInputPanel.add(txtGuestName);
        reservationInputPanel.add(new JLabel("Check-In (dd/MM/yyyy):"));
        txtCheckIn = new JTextField(8);
        reservationInputPanel.add(txtCheckIn);
        reservationInputPanel.add(new JLabel("Check-Out (dd/MM/yyyy):"));
        txtCheckOut = new JTextField(8);
        reservationInputPanel.add(txtCheckOut);
        JButton btnAddReservation = new JButton("Add Reservation");
        btnAddReservation.addActionListener(e -> addReservationAction());
        reservationInputPanel.add(btnAddReservation);
        topPanel.add(reservationInputPanel);

        mainPanel.add(Box.createVerticalStrut(20));

        roomTableModel = new DefaultTableModel(new String[]{"Room Number", "Type", "Price", "Status"}, 0);
        roomTable = new JTable(roomTableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createVerticalStrut(20));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Search (Guest name/Room number):"));
        txtSearch = new JTextField(10);
        bottomPanel.add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> searchAction());
        bottomPanel.add(btnSearch);

        JButton btnDeleteReservation = new JButton("Delete Reservation");
        btnDeleteReservation.addActionListener(e -> deleteReservationAction());
        bottomPanel.add(btnDeleteReservation);

        JButton btnDeleteRoom = new JButton("Delete Room");
        btnDeleteRoom.addActionListener(e -> deleteRoomAction());
        bottomPanel.add(btnDeleteRoom);

        JButton btnSave = new JButton("Save Data");
        btnSave.addActionListener(e -> saveDataAction());
        bottomPanel.add(btnSave);

        JButton btnLoad = new JButton("Load Data");
        btnLoad.addActionListener(e -> loadDataAction());
        bottomPanel.add(btnLoad);

        JButton btnPrintTree = new JButton("Print Room Tree (Console)");
        btnPrintTree.addActionListener(e -> reservationManager.getRoomTree().inorder());
        bottomPanel.add(btnPrintTree);

        mainPanel.add(bottomPanel);
    }

    private void addRoomAction() {
    try {
        int roomNumber = Integer.parseInt(txtRoomNumber.getText());
        String type = txtType.getText();
        double price = Double.parseDouble(txtPrice.getText());
        Room room = new Room(roomNumber, type, price);
        reservationManager.addRoom(room);
        updateRoomTable();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid room number or price!");
    }
}

private void updateRoomTable() {
    roomTableModel.setRowCount(0);
    for (Room room : reservationManager.getRooms()) {
        roomTableModel.addRow(new Object[]{
            room.getRoomNumber(),
            room.getType(),
            room.getPrice(),
            room.isAvailable() ? "Available" : "Occupied"
        });
    }
}

private void addReservationAction() {
    try {
        String guestName = txtGuestName.getText();
        if (guestName.trim().isEmpty()) {
            throw new Exception("Guest name cannot be empty.");
        }
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow == -1) {
            throw new Exception("Please select a room from the table.");
        }
        int roomNumber = (int) roomTableModel.getValueAt(selectedRow, 0);
        Room room = reservationManager.searchRoomByNumber(roomNumber);
        if (room == null) {
            throw new Exception("Selected room not found.");
        }
        if (!room.isAvailable()) {
            throw new Exception("The room is occupied!");
        }
        
        Date checkIn = sdf.parse(txtCheckIn.getText());
        Date checkOut = sdf.parse(txtCheckOut.getText());
        if (checkIn.after(checkOut) || checkIn.equals(checkOut)) {
            throw new Exception("Invalid check-in/check-out dates.");
        }
        Guest guest = new Guest(guestName);
        reservationManager.addGuest(guest);
        Reservation reservation = new Reservation(room, guest, checkIn, checkOut);
        reservationManager.addReservation(reservation);
        updateRoomTable();
        JOptionPane.showMessageDialog(this, "Reservation added successfully.");
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this, "Date format must be dd/MM/yyyy.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

private void deleteReservationAction() {
    String guestName = txtSearch.getText();
    if (guestName.trim().isEmpty()) {
        guestName = JOptionPane.showInputDialog(this, "Enter the name of the guest whose reservation you want to delete:");
    }
    if (guestName == null || guestName.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No name entered.");
        return;
    }
    boolean deleted = reservationManager.deleteReservation(guestName);
    if (deleted) {
        updateRoomTable();
        JOptionPane.showMessageDialog(this, "Reservation for guest " + guestName + " has been successfully deleted.");
    } else {
        JOptionPane.showMessageDialog(this, "No reservation found with this name.");
    }
}

private void deleteRoomAction() {
    int selectedRow = roomTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a room to delete.");
        return;
    }
    int roomNumber = (int) roomTableModel.getValueAt(selectedRow, 0);
    Room room = reservationManager.searchRoomByNumber(roomNumber);
    if (room == null) {
        JOptionPane.showMessageDialog(this, "Room not found.");
        return;
    }
    if (!room.isAvailable()) {
        JOptionPane.showMessageDialog(this, "Cannot delete an occupied room.");
        return;
    }
    boolean removed = reservationManager.deleteRoom(roomNumber);
    if (removed) {
        updateRoomTable();
        JOptionPane.showMessageDialog(this, "Room deleted successfully.");
    } else {
        JOptionPane.showMessageDialog(this, "Failed to delete room.");
    }
}

private void searchAction() {
    String query = txtSearch.getText();
    if (query.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter guest name or room number to search.");
        return;
    }
    try {
        int roomNumber = Integer.parseInt(query);
        Room room = reservationManager.searchRoomByNumber(roomNumber);
        if (room != null) {
            JOptionPane.showMessageDialog(this, room.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Room not found.");
        }
    } catch (NumberFormatException ex) {
        Reservation reservation = reservationManager.searchByGuestName(query);
        if (reservation != null) {
            JOptionPane.showMessageDialog(this, reservation.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Reservation not found.");
        }
    }
}

private void saveDataAction() {
    try {
        reservationManager.saveData("data.txt");
        JOptionPane.showMessageDialog(this, "Data has been saved to data.txt");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error while saving: " + e.getMessage());
    }
}

private void loadDataAction() {
    try {
        reservationManager.loadData("data.txt");
        updateRoomTable();
        JOptionPane.showMessageDialog(this, "Data has been loaded from data.txt");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error while loading: " + e.getMessage());
    }
}

private void loadSampleData() {
    reservationManager.addRoom(new Room(101, "Single", 100.0));
    reservationManager.addRoom(new Room(102, "Double", 150.0));
    reservationManager.addRoom(new Room(103, "Suite", 250.0));
    updateRoomTable();
}
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new HotelReservationSystem().setVisible(true));
}

}