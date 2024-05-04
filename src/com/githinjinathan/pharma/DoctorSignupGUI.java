/*
 * This class represents the GUI for doctor signup.
 * It allows doctors to enter their credentials and sign up or go back to the login page.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorSignupGUI {
    public DoctorSignupGUI() {
        // Create JFrame for doctor signup
        JFrame frame = new JFrame("Doctor Signup");
        frame.setLocation(467,159);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // Labels and TextFields for DoctorID and Doctor Password
        JLabel usernameLabel = new JLabel("DoctorID:");
        usernameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameLabel.setBounds(0,25,1000,24);
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameField.setBounds(0,50,255,24);
        JLabel passwordLabel = new JLabel("Doctor Password:");
        passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordLabel.setBounds(0,75,1000,24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);

        // Buttons for Sign Up and Back
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setBounds(121,150,100,24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121,250,100,24);

        // Add components to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signupButton);
        panel.add(backButton);

        // ActionListener for signup button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get user input
                int username = Integer.parseInt(usernameField.getText());
                String password = new String(passwordField.getPassword());

                // Insert this data into the database using the performDoctorSignup method
                performDoctorSignup(username, password);
            }
        });

        // ActionListener for back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the doctor login page
                new DoctorLoginGUI();
                frame.dispose();// Close the signup page
            }
        });

        frame.setVisible(true);
    }

    // Method to perform doctor signup
    public void performDoctorSignup(int DoctorID, String Doctor_Password) {
        String insertQuery = "INSERT INTO tbl_doctoruserpass (DoctorID, Doctor_Password) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Assuming DoctorID is an auto-incremented field
            preparedStatement.setInt(1, DoctorID);
            preparedStatement.setString(2, Doctor_Password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }
}
