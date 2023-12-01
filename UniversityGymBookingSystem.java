import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UniversityGymBookingSystem extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField studentNameField, dateField, activityField, descriptionField;
    private JButton bookButton, cancelButton, viewButton, sortButton;

    private List<Booking> bookings;

    public UniversityGymBookingSystem() {
        setTitle("University Gym Booking System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setBounds(20, 20, 100, 20);
        add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(120, 20, 150, 20);
        add(studentNameField);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(20, 50, 100, 20);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(120, 50, 150, 20);
        add(dateField);

        JLabel activityLabel = new JLabel("Activity:");
        activityLabel.setBounds(20, 80, 100, 20);
        add(activityLabel);

        activityField = new JTextField();
        activityField.setBounds(120, 80, 150, 20);
        add(activityField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 110, 100, 20);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 110, 150, 20);
        add(descriptionField);

        bookButton = new JButton("Book");
        bookButton.setBounds(20, 150, 80, 20);
        bookButton.addActionListener(this);
        add(bookButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(110, 150, 100, 20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        viewButton = new JButton("View");
        viewButton.setBounds(220, 150, 80, 20);
        viewButton.addActionListener(this);
        add(viewButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(310, 150, 80, 20);
        sortButton.addActionListener(this);
        add(sortButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Date");
        tableModel.addColumn("Activity");
        tableModel.addColumn("Description");

        table = new JTable(tableModel);
        table.setBounds(20, 200, 550, 150);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 550, 150);
        add(scrollPane);

        setVisible(true);

        bookings = new ArrayList<>();
    }

    private void bookActivity() {
        String studentName = studentNameField.getText();
        String date = dateField.getText();
        String activity = activityField.getText();
        String description = descriptionField.getText();

        Booking booking = new Booking(studentName, date, activity, description);
        bookings.add(booking);

        Object[] rowData = {booking.getStudentName(), booking.getDate(), booking.getActivity(), booking.getDescription()};
        tableModel.addRow(rowData);

        clearFields();
    }

    private void cancelBooking() {
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

    private void sortBookingsByStudentName() {
        Collections.sort(bookings, Comparator.comparing(Booking::getStudentName));
        refreshTable();
    }

    private void sortBookingsByDate() {
        Collections.sort(bookings, Comparator.comparing(Booking::getDate));
        refreshTable();
    }

    private void sortBookingsByActivity() {
        Collections.sort(bookings, Comparator.comparing(Booking::getActivity));
        refreshTable();
    }

    private void sortBookingsByDescription() {
        Collections.sort(bookings, Comparator.comparing(Booking::getDescription));
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            Object[] rowData = {booking.getStudentName(), booking.getDate(), booking.getActivity(), booking.getDescription()};
            tableModel.addRow(rowData);
        }
    }

    private void clearFields() {
        studentNameField.setText("");
        dateField.setText("");
        activityField.setText("");
        descriptionField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            bookActivity();
        } else if (e.getSource() == cancelButton) {
            cancelBooking();
        } else if (e.getSource() == viewButton) {
            viewBookings();
        } else if (e.getSource() == sortButton) {
            String selectedItem = JOptionPane.showInputDialog(this, "Sort by:", "Sort Bookings",
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Student Name", "Date", "Activity", "Description"}, "Student Name").toString();

            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Student Name":
                        sortBookingsByStudentName();
                        break;
                    case "Date":
                        sortBookingsByDate();
                        break;
                    case "Activity":
                        sortBookingsByActivity();
                        break;
                    case "Description":
                        sortBookingsByDescription();
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new UniversityGymBookingSystem();
    }

    private class Booking {
        private String studentName;
        private String date;
        private String activity;
        private String description;

        public Booking(String studentName, String date, String activity, String description) {
            this.studentName = studentName;
            this.date = date;
            this.activity = activity;
            this.description = description;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getDate() {
            return date;
        }

        public String getActivity() {
            return activity;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "studentName='" + studentName + '\'' +
                    ", date='" + date + '\'' +
                    ", activity='" + activity + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
