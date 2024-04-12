package Panels;

import Objects.User;
import database.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterWindow extends JDialog{
    private JLabel registerImg;
    private JLabel registerTitle;
    private JLabel nameLbl;
    private JTextField textUsername;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel registerPanel;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JTextField firstNameText;
    private JTextField lastNameText;
    public User user;

    public RegisterWindow(JFrame parent) {
        super(parent);
        setTitle("Register");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login swap = new Login(null);
                swap.setVisible(true);
                swap.pack();
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String firstname = firstNameText.getText();
        String lastname = lastNameText.getText();
        String username = textUsername.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPass = String.valueOf(confirmField.getPassword());
        PreparedStatement ps;
        if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            try {
                ps = dbConnection.getConection().prepareStatement("INSERT INTO user (username, password, firstname, lastname)" + "VALUES (?, ?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, firstname);
                ps.setString(4, lastname);

                int check = ps.executeUpdate();
                if (check > 0) {
                    user = new User();
                    user.username = username;
                    user.password = password;
                    user.firstname = firstname;
                    user.lastname = lastname;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Registration Sucessful", "Created", JOptionPane.INFORMATION_MESSAGE);
            //SWAP TO LOGIN PANEL HERE
            Login swap = new Login(null);
            swap.setVisible(true);
            swap.pack();
            this.dispose();
        }
    }

    public static void main(String[] args) {
         RegisterWindow window = new RegisterWindow(null);
         User user = window.user;
         if (user != null) {
             System.out.println("Registered: " + user.username);
        }
         else {
             System.out.println("Not Registered");
         }
    }
}
