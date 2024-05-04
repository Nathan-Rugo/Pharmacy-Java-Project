/*
 * This class represents a graphical user interface (GUI) for searching drugs.
 * It allows users to search for drugs by name and provides options for different actions based on the search results.
 * Users can search for drugs to view their details or to add them to a patient's drug orders.
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

public class SearchDrugsGUI {
    private JFrame frame;

    public SearchDrugsGUI() {
        // Initialize the GUI frame
        frame = new JFrame("Search Drugs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocation(457, 215);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // GUI components for searching drugs
        JLabel searchLabel = new JLabel("Search Drug");
        searchLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        searchLabel.setBounds(115, 25, 1000, 36);
        JTextField Drug_NameField = new JTextField(20);
        Drug_NameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        Drug_NameField.setBounds(95, 75, 200, 24);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        searchButton.setBounds(139, 150, 100, 24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(139, 175, 100, 24);

        // Add components to the panel
        panel.add(searchLabel);
        panel.add(Drug_NameField);
        panel.add(searchButton);
        panel.add(backButton);

        // Action listener for the "Search" button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle drug search logic
                String Drug_Name = Drug_NameField.getText();
                String option = JOptionPane.showInputDialog(null, "Are searching for a drug for a patient to buy? Y/N ");
                if (option.equalsIgnoreCase("N")) {
                    performSearchDrug(Drug_Name);
                } else if (option.equalsIgnoreCase("Y")) {
                    int patient = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter PatientID: "));
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter amount of drug for patient: "));
                    performSearchDrug(Drug_Name);
                    performAddPatientDrug(Drug_Name, patient, amount);
                }
                // Open a new search drugs page
                new SearchDrugsGUI();
                frame.dispose(); // Close the previous search page
            }
        });

        // Action listener for the "Back" button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page for doctors
                new DoctorMainPage();
                frame.dispose(); // Close the search page
            }
        });
    }

    // Method to perform drug search
    public boolean performSearchDrug(String Drug_Name) {
        String selectQuery = "SELECT * FROM tbl_drugs WHERE Drug_Name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, Drug_Name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a DefaultTableModel to hold the data
                int drugStock = resultSet.getInt("Drug_Stock");
                String status = (drugStock > 0) ? resultSet.getString("Drug_Name") + " is Available!" : resultSet.getString("Drug_Name") + " is not Available!";
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("NDC");
                model.addColumn("Name");
                model.addColumn("Type");
                model.addColumn("Stock");
                model.addColumn("Expiry Date");
                model.addColumn("Price");

                // Populate the table model with data
                model.addRow(new Object[]{
                        resultSet.getInt("NDC"),
                        resultSet.getString("Drug_Name"),
                        resultSet.getString("Drug_Type"),
                        resultSet.getInt("Drug_Stock"),
                        resultSet.getString("Drug_ExpiryDate"),
                        resultSet.getString("Drug_Price")
                });

                // Create a JTable with the model
                JTable table = new JTable(model);

                // Create a JScrollPane to hold the table (in case there are many rows)
                JScrollPane scrollPane = new JScrollPane(table);

                // Show the table in a JOptionPane
                JOptionPane.showMessageDialog(null, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, status);

                return true; // Return true if a matching record is found.
            } else {
                // No matching record found
                JOptionPane.showMessageDialog(null, "Drug is not in the system");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
            return false;
        }
    }

    // Method to add a drug to a patient's drug orders
    public boolean performAddPatientDrug(String drug, int patient, int amount) {
        String selectQuery = "SELECT PatientID FROM tbl_patient WHERE PatientID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(selectQuery)) {
            preparedStatement0.setInt(1, patient);
            ResultSet resultSet = preparedStatement0.executeQuery();
            if (resultSet.next()) {
                String selectQuery2 = "SELECT Drug_Price FROM tbl_drugs WHERE Drug_Name = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectQuery2);
                preparedStatement1.setString(1, drug);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()) {
                    int price = (resultSet1.getInt("Drug_Price")) * amount;
                    String purstatus = "NOT BOUGHT";
                    String orderplacer = "Doctor";
                    String insertQuery = "INSERT INTO tbl_patient_drugorders (PatientID,Drug_Ordered,Amount,Total_Price,Purchase_Status,Order_Placer) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery);
                    preparedStatement2.setInt(1, patient);
                    preparedStatement2.setString(2, drug);
                    preparedStatement2.setInt(3, amount);
                    preparedStatement2.setInt(4, price);
                    preparedStatement2.setString(5, purstatus);
                    preparedStatement2.setString(6, orderplacer);
                    preparedStatement2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Drug added to tbl_patient_drugorders!");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }
}
