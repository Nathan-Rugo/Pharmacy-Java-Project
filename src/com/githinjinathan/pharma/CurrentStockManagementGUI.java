package com.githinjinathan.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class CurrentStockManagementGUI extends JFrame{
    public CurrentStockManagementGUI(){

        JFrame frame =  new JFrame("Manage existing stock");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocation(467,159);
        frame.setResizable(false);

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel searchDrug =  new JLabel("Search Drug");
        searchDrug.setFont(new Font("Century Gothic", Font.BOLD, 24));
        searchDrug.setBounds(0,25,1000,36);
        JTextField drugText = new JTextField(20);
        drugText.setFont(new Font("Century Gothic", Font.BOLD,14));
        drugText.setBounds(0,100,200,24);
        JButton search = new JButton("Search");
        search.setFont(new Font("Century Gothic", Font.BOLD,14));
        search.setBounds(230,100,100,24);

        panel.add(search);
        panel.add(searchDrug);
        panel.add(drugText);

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String drug = drugText.getText();
                if(performSearchDrug(drug)){
                }
                //frame.dispose();
            }
        });
    }
    public boolean performSearchDrug(String drug) {
        String selectQuery = "SELECT Drug_Stock FROM tbl_drugs WHERE Drug_Name = ?";
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)){
                preparedStatement.setString(1,drug);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    int current_stock = resultSet.getInt("Drug_Stock");
                    JOptionPane.showMessageDialog(null,"The current stock of "+drug+" is "+current_stock);
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter amount to be added to current stock:"));
                    String date = JOptionPane.showInputDialog(null,"Enter the expiry date of new stock: ");
                    int newStock = amount+current_stock;
                    updateStockInDatabase(drug,newStock,date);
                    return true;
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean updateStockInDatabase(String Drug_Name, int newStock,String date) {
        String updateQuery = "UPDATE tbl_drugs SET Drug_Stock = ?,Drug_ExpiryDate = ? WHERE Drug_Name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, Drug_Name);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"The new stock of "+Drug_Name+" is "+newStock);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors or display a message to the user.
        }
        return false;
    }

}
