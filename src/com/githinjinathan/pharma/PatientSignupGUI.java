/*
 * This class represents the GUI for patient signup.
 * It allows patients to sign up with their ID, first name, and last name.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientSignupGUI {
    public PatientSignupGUI() {
        JFrame frame = new JFrame("Patient Signup");
        frame.setLocation(467, 159);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        idLabel.setBounds(0, 25, 1000, 24);
        JTextField idField = new JTextField(20);
        idField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        idField.setBounds(100, 25, 255, 24);
        JLabel FNameLabel = new JLabel("First Name:");
        FNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        FNameLabel.setBounds(0, 75, 1000, 24);
        JTextField FNameLabelField = new JTextField(20);
        FNameLabelField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        FNameLabelField.setBounds(100, 75, 255, 24);
        JLabel LNameLabel = new JLabel("Last Name:");
        LNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        LNameLabel.setBounds(0, 100, 1000, 24);
        JTextField LNameLabelField = new JTextField(20);
        LNameLabelField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        LNameLabelField.setBounds(100, 100, 255, 24);

        JButton SignupButton = new JButton("Signup");
        SignupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        SignupButton.setBounds(121, 150, 100, 24);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121, 250, 100, 24);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(FNameLabel);
        panel.add(FNameLabelField);
        panel.add(LNameLabel);
        panel.add(LNameLabelField);
        panel.add(SignupButton);
        panel.add(backButton);

        SignupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement signup logic for patients here
                int PatientID = Integer.parseInt(idField.getText()); // Convert to integer
                String PatientFName = FNameLabelField.getText();
                String PatientLName = LNameLabelField.getText();
                performPatientSignup(PatientID, PatientFName, PatientLName);
                new PatientLoginGUI();
                frame.dispose(); // Close the signup page
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page
                new MainGUI();
                frame.dispose(); // Close the signup page
            }
        });

        frame.setVisible(true);
    }

    // Method to perform patient signup
    public boolean performPatientSignup(int PatientID, String PatientFName, String PatientLName) {
        String insertQuery = "INSERT INTO tbl_patient (PatientID, Patient_FName, Patient_LName, Drug_to_paybal, Drug_Balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            String drug = "N/A";
            int balance = 0;
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, PatientID);
            preparedStatement.setString(2, PatientFName);
            preparedStatement.setString(3, PatientLName);
            preparedStatement.setString(4, drug);
            preparedStatement.setInt(5, balance);
            // Execute the query
            preparedStatement.executeUpdate();
            // Successful signup, show a success message
            JOptionPane.showMessageDialog(null, "Signup Successful!");
            return true;
        } catch (SQLException e) {
            // Signup failed, show an error message
            JOptionPane.showMessageDialog(null, "Signup failed.\nThe PatientID may be taken or you may not have entered all your credentials.");
            e.printStackTrace();
            return false;
        }
    }
}
