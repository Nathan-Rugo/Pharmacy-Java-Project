package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main GUI class for Siwaka Medical Center application
public class MainGUI extends JFrame {
    public MainGUI() {
        // Set up main frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("SIWAKA MEDICAL CENTER - HOME PAGE");
        frame.setLocation(340, 93); // Set frame location
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 485); // Set frame size
        frame.setResizable(false); // Disable frame resizing
        JPanel panel = new JPanel(null); // Create panel with absolute layout
        frame.add(panel);

        // Create main and sub labels
        JLabel main = new JLabel("WELCOME TO SIWAKA MEDICAL CENTER ");
        main.setFont(new Font("Century Gothic", Font.BOLD, 24));
        main.setBounds(64, 0, 1000, 24);
        JLabel sub = new JLabel("What account would you like to sign in as?");
        sub.setFont(new Font("Century Gothic", Font.BOLD, 20));
        sub.setBounds(91, 100, 1000, 24);

        // Create buttons for different user types
        JButton pharmacistButton = new JButton("Pharmacist");
        pharmacistButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        pharmacistButton.setBounds(220, 200, 150, 24);
        JButton doctorButton = new JButton("Doctor");
        doctorButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        doctorButton.setBounds(220, 250, 150, 24);
        JButton patientButton = new JButton("Patient");
        patientButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        patientButton.setBounds(220, 300, 150, 24);

        // Add components to panel
        panel.add(main);
        panel.add(sub);
        panel.add(pharmacistButton);
        panel.add(doctorButton);
        panel.add(patientButton);

        // Action listeners for button clicks
        pharmacistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open pharmacist login page
                new PharmacistLoginGUI();
                frame.dispose(); // Close the main GUI
            }
        });

        doctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open doctor login page
                new DoctorLoginGUI();
                frame.dispose(); // Close the main GUI
            }
        });

        patientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open patient login page
                new PatientLoginGUI();
                frame.dispose(); // Close the main GUI
            }
        });

        frame.setVisible(true); // Make frame visible
    }
}
