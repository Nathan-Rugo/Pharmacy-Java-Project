/*
 * This class represents the GUI for patient login.
 * It allows patients to enter their credentials, either log in, sign up, or go back to the main page.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientLoginGUI {
    public PatientLoginGUI() {
        // Create JFrame for patient login
        JFrame frame = new JFrame("Patient Login");
        frame.setLocation(467,159);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // Labels and TextFields for ID, First Name, and Last Name
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        idLabel.setBounds(0,25,1000,24);
        JTextField idField = new JTextField(20);
        idField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        idField.setBounds(100,25,255,24);
        JLabel FNameLabel = new JLabel("First Name:");
        FNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        FNameLabel.setBounds(0,75,1000,24);
        JTextField FNameLabelField = new JTextField(20);
        FNameLabelField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        FNameLabelField.setBounds(100, 75, 255, 24);
        JLabel LNameLabel = new JLabel("Last Name:");
        LNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        LNameLabel.setBounds(0,100,1000,24);
        JTextField LNameLabelField = new JTextField(20);
        LNameLabelField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        LNameLabelField.setBounds(100, 100, 255, 24);

        // Buttons for Login, Signup, and Back
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        loginButton.setBounds(121,150,100,24);
        JButton signupButton = new JButton("Signup"); // Add a Signup button
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setBounds(121,200,100,24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121,250,100,24);

        // Add components to the panel
        panel.add(idLabel);
        panel.add(idField);
        panel.add(FNameLabel);
        panel.add(FNameLabelField);
        panel.add(LNameLabel);
        panel.add(LNameLabelField);
        panel.add(loginButton);
        panel.add(signupButton);
        panel.add(backButton);

        // ActionListener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered credentials
                int PatientID = Integer.parseInt(idField.getText()); // Convert to integer
                String PatientFName = FNameLabelField.getText();
                String PatientLName = LNameLabelField.getText();
                // Check authentication
                if (performPatientLogin(PatientID, PatientFName,PatientLName)) {
                    // Successful login, open the Patient's main page
                    JOptionPane.showMessageDialog(null,"Success!");
                    new PatientMainPage(PatientID); // Assuming 'false' represents a Patient
                    frame.dispose(); // Close the login page
                } else {
                    // Authentication failed, show an error message to the user
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
                }
            }
        });

        // ActionListener for signup button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the patient signup page
                new PatientSignupGUI();
                frame.dispose(); // Close the login page
            }
        });

        // ActionListener for back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page
                new MainGUI();
                frame.dispose();// Close the login page
            }
        });

        frame.setVisible(true);
    }

    // Method to perform patient login
    public boolean performPatientLogin(int PatientID,String PatientFName,String PatientLName) {
        String selectQuery = "SELECT * FROM tbl_patient WHERE PatientID = ? AND Patient_FName = ? AND Patient_LName = ?   ";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, PatientID);
            preparedStatement.setString(2, PatientFName);
            preparedStatement.setString(3, PatientLName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a matching record is found.
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
            return false;
        }
    }
}
