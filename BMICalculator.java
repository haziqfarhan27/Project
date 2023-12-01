package com.mycompany.bmicalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BMICalculator extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, heightField, weightField;
    private JButton addButton, removeButton, viewButton, sortButton;

    private List<Record> records;

    public BMICalculator() {
        setTitle("BMI Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 20);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 20);
        add(nameField);

        JLabel heightLabel = new JLabel("Height (m):");
        heightLabel.setBounds(20, 50, 100, 20);
        add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(120, 50, 150, 20);
        add(heightField);

        JLabel weightLabel = new JLabel("Weight (kg):");
        weightLabel.setBounds(20, 80, 100, 20);
        add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(120, 80, 150, 20);
        add(weightField);

        addButton = new JButton("Add");
        addButton.setBounds(20, 120, 80, 20);
        addButton.addActionListener(this);
        add(addButton);

        removeButton = new JButton("Remove");
        removeButton.setBounds(110, 120, 100, 20);
        removeButton.addActionListener(this);
        add(removeButton);

        viewButton = new JButton("View");
        viewButton.setBounds(220, 120, 80, 20);
        viewButton.addActionListener(this);
        add(viewButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(310, 120, 80, 20);
        sortButton.addActionListener(this);
        add(sortButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Height (m)");
        tableModel.addColumn("Weight (kg)");
        tableModel.addColumn("BMI");

        table = new JTable(tableModel);
        table.setBounds(20, 160, 550, 150);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 160, 550, 150);
        add(scrollPane);

        setVisible(true);

        records = new ArrayList<>();
    }

    private void addRecord() {
        String name = nameField.getText();
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());

        double bmi = calculateBMI(height, weight);
        Record record = new Record(name, height, weight, bmi);
        records.add(record);

        Object[] rowData = {record.getName(), record.getHeight(), record.getWeight(), record.getBMI()};
        tableModel.addRow(rowData);

        clearFields();
    }

    private double calculateBMI(double height, double weight) {
        // BMI Formula: weight (kg) / (height (m) * height (m))
        return weight / (height * height);
    }

    private void removeRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            records.remove(selectedRow);
        }
    }

    private void viewRecords() {
        for (Record record : records) {
            System.out.println(record);
        }
    }

    private void sortRecordsByName() {
        Collections.sort(records, Comparator.comparing(Record::getName));
        refreshTable();
    }

    private void sortRecordsByHeight() {
        Collections.sort(records, Comparator.comparing(Record::getHeight));
        refreshTable();
    }

    private void sortRecordsByWeight() {
        Collections.sort(records, Comparator.comparing(Record::getWeight));
        refreshTable();
    }

    private void sortRecordsByBMI() {
        Collections.sort(records, Comparator.comparing(Record::getBMI));
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Record record : records) {
            Object[] rowData = {record.getName(), record.getHeight(), record.getWeight(), record.getBMI()};
            tableModel.addRow(rowData);
        }
    }

    private void clearFields() {
        nameField.setText("");
        heightField.setText("");
        weightField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addRecord();
        } else if (e.getSource() == removeButton) {
            removeRecord();
        } else if (e.getSource() == viewButton) {
            viewRecords();
        } else if (e.getSource() == sortButton) {
            String selectedItem = JOptionPane.showInputDialog(this, "Sort by:", "Sort Records",
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Name", "Height", "Weight", "BMI"}, "Name").toString();

            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Name":
                        sortRecordsByName();
                        break;
                    case "Height":
                        sortRecordsByHeight();
                        break;
                    case "Weight":
                        sortRecordsByWeight();
                        break;
                    case "BMI":
                        sortRecordsByBMI();
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new BMICalculator();
    }

    private class Record {
        private String name;
        private double height;
        private double weight;
        private double bmi;

        public Record(String name, double height, double weight, double bmi) {
            this.name = name;
            this.height = height;
            this.weight = weight;
            this.bmi = bmi;
        }

        public String getName() {
            return name;
        }

        public double getHeight() {
            return height;
        }

        public double getWeight() {
            return weight;
        }

        public double getBMI() {
            return bmi;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "name='" + name + '\'' +
                    ", height=" + height +
                    ", weight=" + weight +
                    ", bmi=" + bmi +
                    '}';
        }
    }
}
