/*
 * This class represents the GUI for the doctor's main page.
 * It provides options for doctors such as logging out, changing password, and searching for drugs.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorMainPage {
    private JFrame frame;

    public DoctorMainPage() {
        // Create JFrame for doctor's main page
        frame = new JFrame("Doctor Main Page");
        frame.setLocation(190,186);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 300);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // Label to welcome the doctor
        JLabel nameLabel = new JLabel("WELCOME TO SIWAKA MEDICAL DOCTOR MAIN PAGE");
        nameLabel.setFont(new Font("Century Gothic",Font.BOLD,24));
        nameLabel.setBounds(112,0,1000,24);
        panel.add(nameLabel);

        // Buttons for logout, changing password, and searching drugs
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Century Gothic",Font.BOLD,14));
        logoutButton.setBounds(373,226,150,24);
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Century Gothic",Font.BOLD,14));
        changePasswordButton.setBounds(348,186,200,24);
        JButton searchButton = new JButton("Search Drugs");
        searchButton.setFont(new Font("Century Gothic",Font.BOLD,14));
        searchButton.setBounds(373,100,150,24);
        panel.add(logoutButton);
        panel.add(changePasswordButton);
        panel.add(searchButton);

        // ActionListener for logout button
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle logout logic
                frame.dispose();
                new MainGUI();
            }
        });

        // ActionListener for change password button
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the doctor change password GUI
                new DoctorChangePasswordGUI();
                frame.dispose();
            }
        });

        // ActionListener for search drugs button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the search drugs GUI
                new SearchDrugsGUI();
            }
        });

        frame.setVisible(true);
    }
}
