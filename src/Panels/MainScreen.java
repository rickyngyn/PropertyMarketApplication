package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JDialog{
    private JPanel mainPanel;
    private JLabel propertyManagement;
    private JLabel client;
    private JLabel owners;
    private JLabel sales;

    public MainScreen(JFrame parent) {
        super(parent);
        setTitle("Main");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(parent);
        setVisible(true);
        propertyManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                propertyManagement.setBackground(new Color(98,138,129));
            }
        });

        propertyManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                propertyManagement.setBackground(new Color(130,175,166));
            }
        });

        client.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                client.setBackground(new Color(98,138,129));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                client.setBackground(new Color(130,175,166));

            }
        });
        owners.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                owners.setBackground(new Color(98,138,129));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                owners.setBackground(new Color(130,175,166));

            }
        });
        sales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                sales.setBackground(new Color(98,138,129));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                sales.setBackground(new Color(130,175,166));
            }
        });
        propertyManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PropertyManagement swap = new PropertyManagement(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
        owners.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                OwnersPanel swap = new OwnersPanel(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
        client.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClientPanel swap = new ClientPanel(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
        sales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SalesPanel swap = new SalesPanel(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
    }
}
