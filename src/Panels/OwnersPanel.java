package Panels;

import Objects.Owner;
import database.dbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OwnersPanel extends JDialog {
    private JPanel ownersPanel;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JTable table1;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JScrollPane ownersTable;
    private JTextField idText;
    private JTextField textSearch;

    public OwnersPanel(JFrame parent) {
        super(parent);
        setTitle("Owners");
        setContentPane(ownersPanel);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(parent);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstNameText.getText();
                String lastname = lastNameText.getText();
                String number = phoneNumberText.getText();
                if (firstname.isEmpty() || lastname.isEmpty() || number.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    Owner obj = new Owner();
                    if (obj.addOwner(new Owner(0,firstname, lastname, number))) {
                        JOptionPane.showMessageDialog(null, "Success", "Added Owner", JOptionPane.INFORMATION_MESSAGE);
                        firstNameText.setText("");
                        lastNameText.setText("");
                        phoneNumberText.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Owner not added", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithOwners();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText();
                Owner obj = new Owner();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.removeOwner(Integer.parseInt(id))) {
                        JOptionPane.showMessageDialog(null, "Success", "Removed Owner", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Owner not removed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithOwners();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idText.getText());
                String firstname = firstNameText.getText();
                String lastname = lastNameText.getText();
                String number = phoneNumberText.getText();
                Owner obj = new Owner();
                if ( firstname.isEmpty() || lastname.isEmpty() || number.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.editOwner(new Owner(id,firstname, lastname, number))) {
                        JOptionPane.showMessageDialog(null, "Success", "Edited Owner", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Owner not edited", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithOwners();
            }
        });
        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<>(model);
                table1.setRowSorter(model1);
                model1.setRowFilter(RowFilter.regexFilter(textSearch.getText()));
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = table1.getSelectedRow();
                idText.setText(table1.getValueAt(rowIndex,0).toString());
                firstNameText.setText(table1.getValueAt(rowIndex,1).toString());
                lastNameText.setText(table1.getValueAt(rowIndex, 2).toString());
                phoneNumberText.setText(table1.getValueAt(rowIndex, 3).toString());
            }
        });
        fillJtableWithOwners();
        setVisible(true);
    }
    public void fillJtableWithOwners() {
        Owner obj = new Owner();
        ArrayList<Owner> ownerList = obj.ownerList();
        String[] columns = {"ID","First Name", "Last Name", "Phone"};
        Object[][] rows = new Object[ownerList.size()][4];
        for (int i = 0; i < ownerList.size(); i++) {
            rows[i][0] = ownerList.get(i).getId();
            rows[i][1] = ownerList.get(i).getFirstname();
            rows[i][2] = ownerList.get(i).getLastname();
            rows[i][3] = ownerList.get(i).getNumber();
        }
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        table1.setModel(model);
        table1.setRowHeight(30);
    }
    public static void main(String[] args) {
        OwnersPanel panel = new OwnersPanel(null);
    }
}
