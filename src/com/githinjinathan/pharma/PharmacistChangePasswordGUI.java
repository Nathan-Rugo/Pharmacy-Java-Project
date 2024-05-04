/*
 * This class represents the GUI for pharmacist password change.
 * It allows pharmacists to change their passwords.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PharmacistChangePasswordGUI {
    private JFrame frame;

    public PharmacistChangePasswordGUI() {
        frame = new JFrame("Change Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocation(467, 159);
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        frame.add(panel);
        // GUI components for changing the password
        JLabel userName = new JLabel("PharmacistID");
        userName.setFont(new Font("Century Gothic", Font.BOLD, 14));
        userName.setBounds(0, 25, 1000, 24);
        JTextField userNameField = new JTextField(20);
        userNameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        userNameField.setBounds(0, 50, 255, 24);
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Century Gothic", Font.BOLD, 14));
        password.setBounds(0, 75, 1000, 24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);
        JButton changeButton = new JButton("Change Password");
        changeButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        changeButton.setBounds(71, 150, 200, 24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121, 250, 100, 24);

        panel.add(userName);
        panel.add(userNameField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(changeButton);
        panel.add(backButton);

        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle password change logic
                int username = Integer.parseInt(userNameField.getText());
                String password = new String(passwordField.getPassword());

                // Update the password in the database
                performPharmacistChangePassword(username, password);
                JOptionPane.showMessageDialog(null, "Password Changed");
                //frame.dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Go back to pharmacist main page
                new PharmacistMainPage();
                frame.dispose();
            }
        });
    }

    // Method to update the pharmacist's password in the database
    public void performPharmacistChangePassword(int PharmacistID, String Pharmacist_Password) {
        String updateQuery = "UPDATE tbl_pharmacistuserpass SET Pharmacist_Password = ? WHERE PharmacistID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, Pharmacist_Password);
            preparedStatement.setInt(2, PharmacistID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }

}
