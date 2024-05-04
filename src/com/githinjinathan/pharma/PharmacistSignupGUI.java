/*
 * This class represents a graphical user interface (GUI) for pharmacist signup.
 * It allows pharmacists to create an account by providing their ID and password.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PharmacistSignupGUI extends JFrame {

    public PharmacistSignupGUI() {
        JFrame frame = new JFrame("SIWAKA MEDICAL CENTER");
        frame.setLocation(467, 159);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel usernameLabel = new JLabel("PharmacistID:");
        usernameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameLabel.setBounds(0, 25, 1000, 24);
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameField.setBounds(0, 50, 255, 24);
        JLabel passwordLabel = new JLabel("Pharmacist Password:");
        passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordLabel.setBounds(0, 75, 1000, 24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setBounds(121, 150, 100, 24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121, 250, 100, 24);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signupButton);
        panel.add(backButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get user input
                int username = Integer.parseInt(usernameField.getText());
                String password = new String(passwordField.getPassword());

                // Insert this data into the database using the performPharmacistSignup method
                performPharmacistSignup(username, password);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page for pharmacist login
                new PharmacistLoginGUI();
                frame.dispose(); // Close the current window
            }
        });
        frame.setVisible(true);
    }

    // Method to perform pharmacist signup by inserting data into the database
    public void performPharmacistSignup(int PharmacistID, String Pharmacist_Password) {
        String insertQuery = "INSERT INTO tbl_pharmacistuserpass (PharmacistID, Pharmacist_Password) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Set parameters and execute the query
            preparedStatement.setInt(1, PharmacistID);
            preparedStatement.setString(2, Pharmacist_Password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }
}
