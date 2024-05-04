/*
 * This class represents a graphical user interface (GUI) for stock management options.
 * It provides users with options to either add a new drug or manage existing stock.
 * Upon selecting an option, it opens the respective GUI for that functionality.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockManagementGUI extends JFrame {

    public StockManagementGUI(){
        // Initialize the GUI frame
        JFrame frame = new JFrame("Stock Management!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(600, 300);
        frame.setLocation(400, 200);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        // Instruction label
        JLabel instrLabel = new JLabel("SELECT");
        instrLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        instrLabel.setBounds(271, 25, 1000, 24);

        // Buttons for stock management options
        JButton addNewDrug = new JButton("Add new drug");
        addNewDrug.setFont(new Font("Century Gothic", Font.BOLD, 14));
        addNewDrug.setBounds(221, 100, 175, 24);
        JButton mngcurrentstockButton = new JButton("Manage existing stock");
        mngcurrentstockButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        mngcurrentstockButton.setBounds(181, 150, 255, 24);

        // Add components to the panel
        panel.add(instrLabel);
        panel.add(mngcurrentstockButton);
        panel.add(addNewDrug);

        // Action listener for "Add new drug" button
        addNewDrug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the GUI for adding a new drug
                new AddNewDrugGUI();
                frame.dispose(); // Close the current window
            }
        });

        // Action listener for "Manage existing stock" button
        mngcurrentstockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the GUI for managing existing stock
                new CurrentStockManagementGUI();
                frame.dispose(); // Close the current window
            }
        });
    }
}
