/*
 * This class represents a graphical user interface (GUI) for buying drugs.
 * It allows users to input a patient ID and the name of the drug they wish to purchase.
 * Upon clicking the "Buy Drugs" button, it verifies the availability of the drug in stock,
 * updates the stock quantity, and records the purchase in the database.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyDrugsGUI {
    private JFrame frame;

    public BuyDrugsGUI() {
        // Initialize the GUI frame
        frame = new JFrame("Buy Drugs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(440, 186);
        frame.setSize(400, 300);
        frame.setVisible(true);

        // Create a panel to hold GUI components
        JPanel panel = new JPanel(null);
        frame.add(panel);

        // GUI components for buying drugs
        JLabel patient = new JLabel("PatientID ");
        patient.setFont(new Font("Century Gothic", Font.BOLD, 14));
        patient.setBounds(0, 25, 150, 24);
        JTextField patientField = new JTextField(20);
        patientField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        patientField.setBounds(150, 25, 150, 24);
        JLabel drug = new JLabel("Drug to purchase");
        drug.setFont(new Font("Century Gothic", Font.BOLD, 14));
        drug.setBounds(0, 75, 150, 24);
        JTextField drugField = new JTextField(20);
        drugField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        drugField.setBounds(150, 75, 150, 24);
        JButton buyButton = new JButton("Buy Drugs");
        buyButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        buyButton.setBounds(150, 175, 150, 24);

        // Add GUI components to the panel
        panel.add(patient);
        panel.add(patientField);
        panel.add(drug);
        panel.add(drugField);
        panel.add(buyButton);

        // Action listener for the "Buy Drugs" button
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle drug purchase logic
                String drug = drugField.getText();
                int patient = Integer.parseInt(patientField.getText());
                performBuyDrug(drug, patient);
            }
        });
    }

    // Method to handle drug purchase logic
    public void performBuyDrug(String Drug_Name, int patient) {
        String orderPlacer = "Patient";
        String selectQuery = "SELECT * FROM tbl_patient_drugorders WHERE Drug_Ordered = ? AND PatientID = ? AND Order_Placer = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, Drug_Name);
            preparedStatement.setInt(2, patient);
            preparedStatement.setString(3, orderPlacer);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a table model to display purchase details
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("TableID");
                model.addColumn("PatientID");
                model.addColumn("Drug_Ordered");
                model.addColumn("Amount");
                model.addColumn("Total_Price");
                model.addColumn("Purchase_Status");
                model.addColumn("Total");

                int amount = resultSet.getInt("Amount");

                // Populate the table model with data
                model.addRow(new Object[]{
                        resultSet.getInt("TableID"),
                        resultSet.getInt("PatientID"),
                        resultSet.getString("Drug_Ordered"),
                        amount,
                        resultSet.getString("Total_Price"),
                        resultSet.getString("Purchase_Status"),
                        resultSet.getString("Order_Placer"),
                });

                // Create a JTable with the model
                JTable table = new JTable(model);

                // Create a JScrollPane to hold the table (in case there are many rows)
                JScrollPane scrollPane = new JScrollPane(table);

                // Update stock quantity after purchase
                performUpdateStock(Drug_Name, amount);

            } else {
                // No matching record found
                JOptionPane.showMessageDialog(null, "Drug is not in the system");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }

    // Method to update stock quantity after purchase
    public void performUpdateStock(String Drug_Name, int amount) {
        // Fetch the current stock for the specified drug
        int currentStock = getCurrentStock(Drug_Name);

        if (currentStock > 0) {
            // Subtract the purchased amount from the current stock
            int newStock = currentStock - amount;

            // Update the stock in the database
            updateStockInDatabase(Drug_Name, newStock);

            JOptionPane.showMessageDialog(null, "Stock updated: " + Drug_Name + " - New Stock: " + newStock);
        } else {
            JOptionPane.showMessageDialog(null, Drug_Name + " is out of stock");
        }
    }

    // Method to retrieve current stock quantity of a drug
    private int getCurrentStock(String Drug_Name) {
        String selectQuery = "SELECT Drug_Stock FROM tbl_drugs WHERE Drug_Name = ?";
        int currentStock = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, Drug_Name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentStock = resultSet.getInt("Drug_Stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }

        return currentStock;
    }

    // Method to update stock quantity in the database
    private void updateStockInDatabase(String Drug_Name, int newStock) {
        String updateQuery0 = "UPDATE tbl_drugs SET Drug_Stock = ? WHERE Drug_Name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(updateQuery0)) {
            preparedStatement0.setInt(1, newStock);
            preparedStatement0.setString(2, Drug_Name);
            preparedStatement0.executeUpdate();
            String purstatus = "BOUGHT";
            String updateQuery1 = "UPDATE tbl_patient_drugorders SET Purchase_Status = ? WHERE Drug_Ordered = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateQuery1);{
                preparedStatement1.setString(1, purstatus);
                preparedStatement1.setString(2, Drug_Name);
                preparedStatement1.executeUpdate();}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
