/*
 * This class represents the GUI for pharmacist login.
 * It allows pharmacists to enter their credentials and login to the system.
 * It also provides an option to sign up if they don't have an account.
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

public class PharmacistLoginGUI {
    public PharmacistLoginGUI() {
        JFrame frame = new JFrame("Pharmacist Login");
        frame.setLocation(467, 159);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameLabel.setBounds(0, 25, 1000, 24);
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameField.setBounds(0, 50, 255, 24);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordLabel.setBounds(0, 75, 1000, 24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        loginButton.setBounds(121, 150, 100, 24);
        JButton signupButton = new JButton("Signup"); // Add a Signup button
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setBounds(11, 250, 100, 24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(229, 250, 100, 24);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton); // Add the Signup button
        panel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement authentication logic for pharmacists here
                int pharmacistID = Integer.parseInt(usernameField.getText()); // Convert to integer
                String pharmacistPassword = new String(passwordField.getPassword());
                if (performPharmacistLogin(pharmacistID, pharmacistPassword)) {
                    // Successful login, open the pharmacist's main page
                    new PharmacistMainPage(); // Assuming 'false' represents a pharmacist
                    frame.dispose(); // Close the login page
                } else {
                    // Authentication failed, show an error message to the user
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
                }
                // Check username and password against a database
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the pharmacist signup page
                new PharmacistSignupGUI();
                frame.dispose(); // Close the login page
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page
                new MainGUI();
                frame.dispose();// Close the login page
            }
        });

        frame.setVisible(true);
    }

    public boolean performPharmacistLogin(int PharmacistID, String Pharmacist_Password) {
        String selectQuery = "SELECT * FROM tbl_pharmacistuserpass WHERE PharmacistID = ? AND Pharmacist_Password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, PharmacistID);
            preparedStatement.setString(2, Pharmacist_Password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a matching record is found.
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
            return false;
        }
    }
}
