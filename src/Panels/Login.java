package Panels;

import Objects.User;
import database.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JDialog{
    private JTextField textUsername;
    private JPasswordField textPassword;
    private JButton logInButton;
    private JButton cancelButton;
    private JButton donTHaveAnButton;
    private JPanel login;
    public User user;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(login);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(parent);
        textUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (textUsername.getText().trim().toLowerCase().equals("username")) {
                    textUsername.setText("");
                }
            }
        });
        textUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (textUsername.getText().trim().equals("") || textUsername.getText().trim().toLowerCase().equals("username")) {
                    textUsername.setText("Username");
                }
            }
        });
        textPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (String.valueOf(textPassword.getPassword()).trim().toLowerCase().equals("password")) {
                    textPassword.setText("");
                }
            }
        });
        textPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (String.valueOf(textPassword.getPassword()).trim().toLowerCase().equals("") ||
                        String.valueOf(textPassword.getPassword()).trim().toLowerCase().equals("password")) {
                    textPassword.setText("Password");
                }
            }
        });
        donTHaveAnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegisterWindow swap = new RegisterWindow(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //swap to main screen if user and pass match database
                String username = textUsername.getText();
                String password = String.valueOf(textPassword.getPassword());
                user = authentication(username, password);

                if (user != null)  {
                    //swap to main form
                    dispose();
                    MainScreen swap = new MainScreen(null);
                    swap.setVisible(true);
                    swap.pack();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password", "Try again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true); //should always be last
    }

    private User authentication(String username, String password) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = new User();
                user.username = result.getString("username");
                user.password = result.getString("password");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        Login userWindow = new Login(null);
    }
}
