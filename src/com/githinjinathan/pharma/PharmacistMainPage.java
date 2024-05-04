/*
 * This class represents the main page for the pharmacist.
 * It provides options for the pharmacist to perform various tasks such as buying drugs, managing stock, etc.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacistMainPage {
    private JFrame frame;

    public PharmacistMainPage() {
        frame = new JFrame("Pharmacist Main Page");
        frame.setLocation(190, 186);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 300);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel nameLabel = new JLabel("WELCOME TO SIWAKA MEDICAL PHARMACIST MAIN PAGE");
        nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        nameLabel.setBounds(112, 0, 1000, 24);
        panel.add(nameLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        logoutButton.setBounds(373, 226, 150, 24);
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        changePasswordButton.setBounds(348, 186, 200, 24);
        JButton rmvexpiredButton = new JButton("Remove expired drugs");
        rmvexpiredButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        rmvexpiredButton.setBounds(348, 100, 200, 24);
        JButton stockmngButton = new JButton("Add New Stock");
        stockmngButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        stockmngButton.setBounds(663, 100, 150, 24);
        JButton buyDrugsButton = new JButton("Buy Drugs");
        buyDrugsButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        buyDrugsButton.setBounds(88, 100, 150, 24);

        panel.add(logoutButton);
        panel.add(changePasswordButton);
        panel.add(rmvexpiredButton);
        panel.add(stockmngButton);
        panel.add(buyDrugsButton);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle logout logic
                frame.dispose(); // Close the current window
                new MainGUI(); // Open the main page
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the page for changing password
                new PharmacistChangePasswordGUI();
                frame.dispose(); // Close the current window
            }
        });

        stockmngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the stock management page
                new StockManagementGUI();
            }
        });

        buyDrugsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the page for buying drugs
                new BuyDrugsGUI();
            }
        });

        rmvexpiredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the page for removing expired drugs
                new RemoveExpiredDrugs();
            }
        });
    }
}
