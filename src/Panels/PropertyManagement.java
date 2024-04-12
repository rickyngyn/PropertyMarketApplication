package Panels;

import Objects.property;
import com.mysql.cj.jdbc.JdbcConnection;
import database.dbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PropertyManagement extends JDialog{
    private JPanel propertyManagement;
    private JComboBox comboBox1;
    private JTextField ownerName;
    private JTextField squareFeet;
    private JTextField priceText;
    private JTextField addressText;
    private JTextField bedroomsText;
    private JTextField bathroomsText;
    private JTextField description;
    private JTable propertyTable;
    private JButton removeButton;
    private JButton editButton;
    private JButton addButton1;
    private JTextField idText;
    private JTextField searchText;
    private JButton imageButton;

    public PropertyManagement(JFrame parent) {
        super(parent);
        setTitle("Property Management");
        setContentPane(propertyManagement);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(parent);
        addButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type, owner, address, desc;
                String squarefeet, price, bedrooms, bathrooms;
                type = (String) comboBox1.getSelectedItem();
                owner = ownerName.getText();
                address = addressText.getText();
                desc = description.getText();
                squarefeet = squareFeet.getText();
                price = priceText.getText();
                bedrooms = bedroomsText.getText();
                bathrooms = bathroomsText.getText();
                if (type.trim().equals("Select Property Type") || owner.isEmpty() || address.isEmpty() || squarefeet.isEmpty() || price.isEmpty() || bedrooms.isEmpty() || bathrooms.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (ownerAuthentication(Integer.parseInt(owner.trim()))) {
                        property obj = new property();
                        if (obj.addNewProperty(new property(0, type, Integer.parseInt(owner.trim()),price, address, Integer.parseInt(bedrooms.trim()), Integer.parseInt(bathrooms.trim()), desc, squarefeet))) {
                            JOptionPane.showMessageDialog(null, "Property Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                            comboBox1.setSelectedItem("Select Property Type");
                            ownerName.setText("");
                            addressText.setText("");
                            description.setText("");
                            squareFeet.setText("");
                            priceText.setText("");
                            bedroomsText.setText("");
                            bathroomsText.setText("");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Property Not Added", "Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Property Not Added", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtable();

            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText();
                property obj = new property();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No ID", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.removeProperty(Integer.parseInt(id))) {
                        JOptionPane.showMessageDialog(null, "Property Removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Property Not Removed", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtable();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(idText.getText().trim());
            String type = comboBox1.getSelectedItem().toString();
            String owner = ownerName.getText();
            String price = priceText.getText();
            String address = addressText.getText();
            String squarefeet = squareFeet.getText();
            String bedrooms = bedroomsText.getText();
            String bathrooms = bathroomsText.getText();
            String desc = description.getText();
            property obj = new property();
            if (type.trim().equals("Select Property Type") || owner.isEmpty() || price.isEmpty() || address.isEmpty() || bedrooms.isEmpty() || bathrooms.isEmpty() || squarefeet.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if (ownerAuthentication(Integer.parseInt(owner.trim()))) {
                    if (obj.editProperty(new property(id, type, Integer.parseInt(owner.trim()), price, address, Integer.parseInt(bedrooms.trim()), Integer.parseInt(bathrooms.trim()), desc, squarefeet))) {
                        JOptionPane.showMessageDialog(null, "Property Edited", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Property Not Edited", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Property Not Edited", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
            fillJtable();
            }
        });
        propertyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = propertyTable.getSelectedRow();
                idText.setText(propertyTable.getValueAt(rowIndex,0).toString());
                comboBox1.setSelectedItem(propertyTable.getValueAt(rowIndex, 1));
                ownerName.setText(propertyTable.getValueAt(rowIndex,2).toString());
                squareFeet.setText(propertyTable.getValueAt(rowIndex,3).toString());
                priceText.setText(propertyTable.getValueAt(rowIndex, 4).toString());
                addressText.setText(propertyTable.getValueAt(rowIndex, 5).toString());
                bedroomsText.setText(propertyTable.getValueAt(rowIndex,6).toString());
                bathroomsText.setText(propertyTable.getValueAt(rowIndex,7).toString());
                description.setText(propertyTable.getValueAt(rowIndex,8).toString());
            }
        });
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();
                TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<>(model);
                propertyTable.setRowSorter(model1);
                model1.setRowFilter(RowFilter.regexFilter(searchText.getText()));
            }
        });
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertyImages swap = new PropertyImages(null);
                swap.setVisible(true);
                swap.pack();
            }
        });
        fillJtable();
        setVisible(true);
    }
    private boolean ownerAuthentication(int owner) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("SELECT * FROM personalownerslist WHERE id = ?");
            ps.setInt(1, owner);
            ResultSet result = ps.executeQuery();
            return (result.next());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void fillJtable() {
        property obj = new property();
        ArrayList<property> propertyList = obj.propertyList();
        String[] col = {"ID", "Type", "Owner ID", "Square Feet", "Price", "Address", "Bedrooms", "Bathrooms", "Description"};
        Object[][] rows = new Object[propertyList.size()][9];
        for (int i = 0; i < propertyList.size(); i++) {
            rows[i][0] = propertyList.get(i).getId();
            rows[i][1] = propertyList.get(i).getType();
            rows[i][2] = propertyList.get(i).getOwner();
            rows[i][3] = propertyList.get(i).getSquarefeet();
            rows[i][4] = propertyList.get(i).getPrice();
            rows[i][5] = propertyList.get(i).getAddress();
            rows[i][6] = propertyList.get(i).getBedrooms();
            rows[i][7] = propertyList.get(i).getBathrooms();
            rows[i][8] = propertyList.get(i).getDescription();
        }
        DefaultTableModel model = new DefaultTableModel(rows, col);
        propertyTable.setModel(model);
        propertyTable.setRowHeight(30);
    }
    public static void main(String[] args) {
        PropertyManagement panel = new PropertyManagement(null);
    }
}

