import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HotelReservationSystem extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, dateField, roomTypeField, descriptionField;
    private JButton addButton, removeButton, viewButton, sortButton;

    private List<Booking> bookings;

    public HotelReservationSystem() {
        setTitle("Hotel Room Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 20);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 20);
        add(nameField);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(20, 50, 100, 20);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(120, 50, 150, 20);
        add(dateField);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(20, 80, 100, 20);
        add(roomTypeLabel);

        roomTypeField = new JTextField();
        roomTypeField.setBounds(120, 80, 150, 20);
        add(roomTypeField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 110, 100, 20);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 110, 150, 20);
        add(descriptionField);

        addButton = new JButton("Add");
        addButton.setBounds(20, 150, 80, 20);
        addButton.addActionListener(this);
        add(addButton);

        removeButton = new JButton("Remove");
        removeButton.setBounds(110, 150, 100, 20);
        removeButton.addActionListener(this);
        add(removeButton);

        viewButton = new JButton("View");
        viewButton.setBounds(220, 150, 80, 20);
        viewButton.addActionListener(this);
        add(viewButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(310, 150, 80, 20);
        sortButton.addActionListener(this);
        add(sortButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Date");
        tableModel.addColumn("Room Type");
        tableModel.addColumn("Description");

        table = new JTable(tableModel);
        table.setBounds(20, 200, 550, 150);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 550, 150);
        add(scrollPane);

        setVisible(true);

        bookings = new ArrayList<>();
    }

    private void addBooking() {
        String name = nameField.getText();
        String date = dateField.getText();
        String roomType = roomTypeField.getText();
        String description = descriptionField.getText();

        Booking booking = new Booking(name, date, roomType, description);
        bookings.add(booking);

        Object[] rowData = {booking.getName(), booking.getDate(), booking.getRoomType(), booking.getDescription()};
        tableModel.addRow(rowData);

        clearFields();
    }

    private void removeBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            bookings.remove(selectedRow);
        }
    }

    private void viewBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    private void sortBookingsByName() {
        Collections.sort(bookings, Comparator.comparing(Booking::getName));
        refreshTable();
    }

    private void sortBookingsByDate() {
        Collections.sort(bookings, Comparator.comparing(Booking::getDate));
        refreshTable();
    }

    private void sortBookingsByRoomType() {
        Collections.sort(bookings, Comparator.comparing(Booking::getRoomType));
        refreshTable();
    }

    private void sortBookingsByDescription() {
        Collections.sort(bookings, Comparator.comparing(Booking::getDescription));
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            Object[] rowData = {booking.getName(), booking.getDate(), booking.getRoomType(), booking.getDescription()};
            tableModel.addRow(rowData);
        }
    }

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        roomTypeField.setText("");
        descriptionField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addBooking();
        } else if (e.getSource() == removeButton) {
            removeBooking();
        } else if (e.getSource() == viewButton) {
            viewBookings();
        } else if (e.getSource() == sortButton) {
            String selectedItem = JOptionPane.showInputDialog(this, "Sort by:", "Sort Bookings",
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Name", "Date", "Room Type", "Description"}, "Name").toString();

            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Name":
                        sortBookingsByName();
                        break;
                    case "Date":
                        sortBookingsByDate();
                        break;
                    case "Room Type":
                        sortBookingsByRoomType();
                        break;
                    case "Description":
                        sortBookingsByDescription();
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new HotelReservationSystem();
    }

    private class Booking {
        private String name;
        private String date;
        private String roomType;
        private String description;

        public Booking(String name, String date, String roomType, String description) {
            this.name = name;
            this.date = date;
            this.roomType = roomType;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "name='" + name + '\'' +
                    ", date='" + date + '\'' +
                    ", roomType='" + roomType + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
