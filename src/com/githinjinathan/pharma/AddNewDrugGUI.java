/*
 * This class represents a graphical user interface (GUI) for adding new drugs to the stock management system.
 * It allows users to input details such as NDC, drug name, type, stock quantity, expiry date, and price.
 * Upon clicking the "Add Stock" button, it inserts the new drug details into the database.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewDrugGUI {
    private JFrame frame;

    public AddNewDrugGUI() {
        // Initialize the GUI frame
        frame = new JFrame("Stock Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setLocation(467, 199);
        frame.setResizable(false);
        frame.setVisible(true);
        JPanel panel = new JPanel(null);
        frame.add(panel);

        // GUI components for managing stock
        JLabel NDC = new JLabel("NDC");
        NDC.setFont(new Font("Century Gothic", Font.BOLD, 14));
        NDC.setBounds(0, 25, 1000, 24);
        JTextField NDCField = new JTextField(20);
        NDCField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        NDCField.setBounds(100, 25, 150, 24);
        JLabel Drug_Name = new JLabel("Drug Name");
        Drug_Name.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Drug_Name.setBounds(0, 50, 1000, 24);
        JTextField Drug_NameField = new JTextField(20);
        Drug_NameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Drug_NameField.setBounds(100, 50, 150, 24);
        JLabel Drug_Type = new JLabel("Drug Type");
        Drug_Type.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Drug_Type.setBounds(0, 75, 1000, 24);
        JTextField Drug_TypeField = new JTextField(20);
        Drug_TypeField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Drug_TypeField.setBounds(100, 75, 150, 24);
        JLabel Stock = new JLabel("Stock");
        Stock.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Stock.setBounds(0, 100, 1000, 24);
        JTextField StockField = new JTextField(20);
        StockField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        StockField.setBounds(100, 100, 150, 24);
        JLabel Expiry_Date = new JLabel("Expiry Date");
        Expiry_Date.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Expiry_Date.setBounds(0, 125, 1000, 24);
        JTextField Expiry_DateField = new JTextField(20);
        Expiry_DateField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Expiry_DateField.setBounds(100, 125, 150, 24);
        JLabel Price = new JLabel("Price");
        Price.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Price.setBounds(0, 150, 1000, 24);
        JTextField PriceField = new JTextField(20);
        PriceField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        PriceField.setBounds(100, 150, 150, 24);

        JButton addButton = new JButton("Add Stock");
        addButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        addButton.setBounds(101, 250, 150, 24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121, 300, 100, 24);

        // Add GUI components to the panel
        panel.add(NDC);
        panel.add(NDCField);
        panel.add(Drug_Name);
        panel.add(Drug_NameField);
        panel.add(Drug_Type);
        panel.add(Drug_TypeField);
        panel.add(Stock);
        panel.add(StockField);
        panel.add(Expiry_Date);
        panel.add(Expiry_DateField);
        panel.add(Price);
        panel.add(PriceField);
        panel.add(addButton);
        panel.add(backButton);

        // Action listener for the "Add Stock" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle stock management logic
                int NDC = Integer.parseInt(NDCField.getText());
                String Drug_Name = Drug_NameField.getText();
                String Drug_Type = Drug_TypeField.getText();
                int Stock = Integer.parseInt(StockField.getText());
                String Expiry_Date = Expiry_DateField.getText();
                performAddStock(NDC, Drug_Name, Drug_Type, Stock, Expiry_Date);
                JOptionPane.showMessageDialog(null, "Stock Added!");
                // Refresh the page after adding stock
                new AddNewDrugGUI();
            }
        });

        // Action listener for the "Back" button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page
                new PharmacistMainPage();
                frame.dispose(); // Close the current page
            }
        });
    }

    // Method to perform addition of stock into the database
    public void performAddStock(int NDC, String Drug_Name, String Drug_Type, int Drug_Stock, String Drug_ExpiryDate) {
        String insertQuery = "INSERT INTO tbl_drugs (NDC, Drug_Name,Drug_Type, Drug_Stock, Drug_ExpiryDate) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Set values for the prepared statement
            preparedStatement.setInt(1, NDC);
            preparedStatement.setString(2, Drug_Name);
            preparedStatement.setString(3, Drug_Type);
            preparedStatement.setInt(4, Drug_Stock);
            preparedStatement.setString(5, Drug_ExpiryDate);

            // Execute the insert query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }
}
