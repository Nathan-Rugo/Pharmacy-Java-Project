/*
 * This class represents a graphical user interface (GUI) for removing expired drugs from the database.
 * It prompts the user to confirm if they want to remove expired drugs and then performs the removal process.
 */

package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

import static com.githinjinathan.pharma.DBConnection.*;

public class RemoveExpiredDrugs extends JFrame {
    private final JFrame frame;

    public RemoveExpiredDrugs() {
        // Initialize the GUI frame
        frame = new JFrame("Check Expired Drugs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(298, 198);
        frame.setSize(689, 253);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);
        JLabel instrLabel = new JLabel("Would you like to remove expired drugs!");
        instrLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        instrLabel.setBounds(149, 25, 1000, 28);
        JButton yesButton = new JButton("YES");
        yesButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        yesButton.setBounds(268, 100, 150, 24);
        JButton noButton = new JButton("NO");
        noButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        noButton.setBounds(268, 150, 150, 24);

        panel.add(instrLabel);
        panel.add(yesButton);
        panel.add(noButton);

        frame.setVisible(true);

        // Action listener for "NO" button
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the main page for pharmacists
                new PharmacistMainPage();
                frame.dispose(); // Close the current window
            }
        });

        // Action listener for "YES" button
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the current date
                LocalDate date = LocalDate.now();
                // Perform removal of expired drugs
                performFindExpiredDrugs(date);
            }
        });
    }

    // Method to find and remove expired drugs from the database
    public boolean performFindExpiredDrugs(LocalDate date) {
        String selectQuery = "SELECT Drug_Name, Drug_ExpiryDate FROM tbl_drugs WHERE ? >= Drug_ExpiryDate";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // At least one expired drug was found
                JOptionPane.showMessageDialog(null, "EXPIRY DATE FOUND");

                do {
                    String drugName = resultSet.getString("Drug_Name");
                    // Remove the expired drug
                    if (performRmvExpiredDrugs(drugName)) {
                        JOptionPane.showMessageDialog(null, drugName + " has been removed!");
                        JOptionPane.showMessageDialog(null, "New stock for " + drugName + " is 0!\n Please buy new stock!");
                    }
                } while (resultSet.next());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXPIRY DATE FOUND");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // Method to remove an expired drug from the database
    public boolean performRmvExpiredDrugs(String drugName) {
        String updateQuery = "UPDATE tbl_drugs SET Drug_Stock = 0, Drug_ExpiryDate = NULL WHERE Drug_Name=?";
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
