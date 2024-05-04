/*
 * This class represents the graphical user interface (GUI) for changing doctor passwords.
 * It allows doctors to change their passwords by entering their doctor ID and new password.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorChangePasswordGUI {
    private JFrame frame;

    public DoctorChangePasswordGUI() {
        // Create JFrame for changing password
        frame = new JFrame("Change Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocation(467,159);
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // GUI components for changing the password
        JLabel userName = new JLabel("DoctorID");
        userName.setFont(new Font("Century Gothic", Font.BOLD, 14));
        userName.setBounds(0,25,1000,24);
        JTextField userNameField = new JTextField(20);
        userNameField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        userNameField.setBounds(0,50,255,24);
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Century Gothic", Font.BOLD, 14));
        password.setBounds(0,75,1000,24);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        passwordField.setBounds(0, 100, 255, 24);
        JButton changeButton = new JButton("Change Password");
        changeButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        changeButton.setBounds(71,150,200,24);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        backButton.setBounds(121,250,100,24);

        // Add components to the panel
        panel.add(userName);
        panel.add(userNameField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(changeButton);
        panel.add(backButton);

        // ActionListener for change password button
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle password change logic
                int username = Integer.parseInt(userNameField.getText());
                String password = new String(passwordField.getPassword());

                // Update the password in the database
                performDoctorChangePassword(username, password);
                JOptionPane.showMessageDialog(null,"Password Changed");
                new DoctorLoginGUI(); // Open doctor login page
                frame.dispose(); // Close the change password page
            }
        });

        // ActionListener for back button
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new DoctorMainPage(); // Open doctor main page
                frame.dispose(); // Close the change password page
            }
        });
    }

    // Method to show the frame
    public void show() {
        frame.setVisible(true);
    }

    // Method to perform doctor password change
    public void performDoctorChangePassword(int DoctorID, String Doctor_Password) {
        String updateQuery = "UPDATE tbl_doctoruserpass SET Doctor_Password = ? WHERE DoctorID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, Doctor_Password);
            preparedStatement.setInt(2, DoctorID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
    }

}
