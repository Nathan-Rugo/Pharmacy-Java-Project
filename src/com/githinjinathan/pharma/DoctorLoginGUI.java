/*
 * This class represents the graphical user interface (GUI) for doctor login.
 * It allows doctors to enter their credentials and login to the system.
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

public class DoctorLoginGUI {
    public DoctorLoginGUI() {
        // Create JFrame for doctor login
        JFrame frame = new JFrame("Doctor Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocation(467,159);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // Labels and text fields for username and password
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameLabel.setBounds(0,25,1000,24);
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        usernameField.setBounds(0,50,255,24);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordLabel.setBounds(0,75,1000,24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);

        // Buttons for login, signup, and back
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        loginButton.setBounds(121,150,100,24);
        JButton signupButton = new JButton("Signup"); // Add a Signup button
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setBounds(11,250,100,24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(229,250,100,24);

        // Add components to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton); // Add the Signup button
        panel.add(backButton);

        // ActionListener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int doctorID = Integer.parseInt(usernameField.getText()); // Convert to integer
                String doctorPassword = new String(passwordField.getPassword());
                if (performDoctorLogin(doctorID, doctorPassword)){
                    // Successful login, open the doctor's main page
                    new DoctorMainPage(); // Assuming 'false' represents a doctor
                    frame.dispose(); // Close the login page
                }else {
                    // Authentication failed, show an error message to the user
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
                }
            }
        });

        // ActionListener for signup button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the doctor signup page
                new DoctorSignupGUI();
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

    // Method to perform doctor login
    public boolean performDoctorLogin(int DoctorID, String Doctor_Password) {
        String selectQuery = "SELECT * FROM tbl_doctoruserpass WHERE DoctorID = ? AND Doctor_Password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, DoctorID);
            preparedStatement.setString(2, Doctor_Password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a matching record is found.
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
            return false;
        }
    }
}
