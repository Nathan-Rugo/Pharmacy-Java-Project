/*
 * This class represents the GUI for the patient's main page after logging in.
 * It allows patients to perform actions such as ordering drugs, checking their balance, and logging out.
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

public class PatientMainPage {
    private JFrame frame;

    // Constructor for PatientMainPage
    public PatientMainPage(int PatientID) {
        // Initialize JFrame
        frame = new JFrame("Patient Main Page");
        frame.setLocation(190, 186);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 300);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // JLabel for welcome message
        JLabel nameLabel = new JLabel("WELCOME TO SIWAKA MEDICAL PATIENT MAIN PAGE");
        nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        nameLabel.setBounds(112, 0, 1000, 24);
        panel.add(nameLabel);

        // JButtons for actions: Logout, Order Drugs, Check Balance
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        logoutButton.setBounds(373, 226, 150, 24);
        JButton orderDrugsButton = new JButton("Order Drugs");
        orderDrugsButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        orderDrugsButton.setBounds(348, 125, 200, 24);
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        balanceButton.setBounds(348, 100, 200, 24);

        panel.add(logoutButton);
        panel.add(orderDrugsButton);
        panel.add(balanceButton);

        // ActionListener for ordering drugs
        orderDrugsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Prompt user for drug name and amount
                String drug_name = JOptionPane.showInputDialog(null, "What drug would you like to order?");
                int drug_amount = Integer.parseInt(JOptionPane.showInputDialog(null, "What amount of " + drug_name + " would you like to order?"));
                // Call performAddOrder method to add the order
                performAddOrder(PatientID, drug_name, drug_amount);
            }
        });

        // ActionListener for checking balance
        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call performCheckBalance method to check balance
                performCheckBalance(PatientID);
            }
        });

        // ActionListener for logging out
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the frame and return to the main page
                frame.dispose();
                new MainGUI();
            }
        });
    }

    // Method to perform adding an order for drugs
    public boolean performAddOrder(int patientID, String drug_name, int drugAmount) {
        String selectQuery = "SELECT PatientID FROM tbl_patient WHERE PatientID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(selectQuery)) {
            preparedStatement0.setInt(1, patientID);
            ResultSet resultSet = preparedStatement0.executeQuery();
            if (resultSet.next()) {
                String selectQuery2 = "SELECT Drug_Price FROM tbl_drugs WHERE Drug_Name = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectQuery2);
                preparedStatement1.setString(1, drug_name);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()) {
                    int price = (resultSet1.getInt("Drug_Price")) * drugAmount;
                    String purstatus = "NOT BOUGHT";
                    String orderplacer = "Patient";
                    String insertQuery = "INSERT INTO tbl_patient_drugorders (PatientID,Drug_Ordered,Amount,Total_Price,Purchase_Status,Order_Placer) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery);
                    preparedStatement2.setInt(1, patientID);
                    preparedStatement2.setString(2, drug_name);
                    preparedStatement2.setInt(3, drugAmount);
                    preparedStatement2.setInt(4, price);
                    preparedStatement2.setString(5,purstatus);
                    preparedStatement2.setString(6,orderplacer);
                    preparedStatement2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Order placed!");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    // Method to perform checking the balance
    public void performCheckBalance(int patientID) {
        String conditionStatus = "NOT BOUGHT";
        String conditionplacer = "Doctor";
        String selectQuery = "SELECT * FROM tbl_patient_drugorders WHERE Purchase_Status = ? AND Order_Placer = ? AND PatientID = ? ";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(selectQuery)) {
            preparedStatement0.setString(1,conditionStatus);
            preparedStatement0.setString(2,conditionplacer);
            preparedStatement0.setInt(3, patientID);
            ResultSet resultSet0 = preparedStatement0.executeQuery();
            if (resultSet0.next()) {
                String selectedDrug = resultSet0.getString("Drug_Ordered");
                int sum = resultSet0.getInt("Total_Price");
                int amount = resultSet0.getInt("Amount");
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Drug Ordered");
                model.addColumn("Amount");
                model.addColumn("Total Price");
                model.addColumn("Purchase Status");

                // Populate the table model with data
                model.addRow(new Object[]{
                        resultSet0.getString("Drug_Ordered"),
                        resultSet0.getString("Amount"),
                        resultSet0.getInt("Total_Price"),
                        resultSet0.getString("Purchase_Status")
                });

                // Create a JTable with the model
                JTable table = new JTable(model);

                // Create a JScrollPane to hold the table (in case there are many rows)
                JScrollPane scrollPane = new JScrollPane(table);

                // Show the table in a JOptionPane
                JOptionPane.showMessageDialog(null, scrollPane, "Details of Drugs with a Balance Result", JOptionPane.INFORMATION_MESSAGE);

                String updateQuery = "UPDATE tbl_patient SET Drug_Balance = ?, Drug_to_paybal = ? WHERE PatientID = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(updateQuery);
                preparedStatement1.setInt(1, sum);
                preparedStatement1.setString(2, selectedDrug);
                preparedStatement1.setInt(3, patientID);
                preparedStatement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Your Balance is " + sum + " !");

                String option = JOptionPane.showInputDialog(null, "Would you like to clear your balance and get your drug(s)! Y/N");
                if (option.equals("Y") || option.equals("y")) {
                    String drugname = selectedDrug;
                    performBalSelect(patientID,sum,drugname,amount);
                    JOptionPane.showMessageDialog(null,"Balance has been cleared for all instances of "+drugname);

                } else if (option.equals("N") || option.equals("n")) {
                    JOptionPane.showMessageDialog(null, "Please pay to get your orders drugs!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter Y OR y to accept or N or n to decline");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You have no balance!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to perform selecting balance
    public void performBalSelect(int patientID, int sum, String drug, int amount) {
        String selectQuery0 = "SELECT Total_Price FROM tbl_patient_drugorders WHERE PatientID = ? AND Drug_Ordered = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(selectQuery0)) {
            preparedStatement0.setInt(1, patientID);
            preparedStatement0.setString(2, drug);
            ResultSet resultSet0 = preparedStatement0.executeQuery();
            if (resultSet0.next()) {
                int amntdeducted = resultSet0.getInt("Total_Price");
                int newbal = sum - amntdeducted;
                String status = "BOUGHT";
                performUpdate(newbal, patientID, drug, status, amount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to perform updating balance
    public boolean performUpdate(int newbal, int patientID, String drug, String status, int amount) {
        String updateQuery0 = "UPDATE tbl_patient_drugorders SET Purchase_Status = ? WHERE PatientID = ? AND Drug_Ordered = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement0 = connection.prepareStatement(updateQuery0)) {
            preparedStatement0.setString(1, status);
            preparedStatement0.setInt(2, patientID);
            preparedStatement0.setString(3, drug);
            preparedStatement0.executeUpdate();

            String updateQuery1 = "UPDATE tbl_patient SET Drug_Balance = ? ,Drug_to_paybal = ? WHERE PatientID = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateQuery1);
            String nul = "N/A";
            preparedStatement1.setInt(1, newbal);
            preparedStatement1.setString(2,nul);
            preparedStatement1.setInt(3, patientID);
            preparedStatement1.executeUpdate();

            String selectQuery0 = "SELECT Drug_Stock FROM tbl_drugs WHERE Drug_Name = ?";
            PreparedStatement preparedStatement3 = connection.prepareStatement(selectQuery0);
            preparedStatement3.setString(1, drug);
            ResultSet resultSet = preparedStatement3.executeQuery();
            if (resultSet.next()) {
                int current_stock = resultSet.getInt("Drug_Stock");
                int new_stock = current_stock - amount;

                String updateQuery2 = "UPDATE tbl_drugs SET Drug_Stock = ? WHERE Drug_Name = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(updateQuery2);
                preparedStatement2.setInt(1, new_stock);
                preparedStatement2.setString(2, drug);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
