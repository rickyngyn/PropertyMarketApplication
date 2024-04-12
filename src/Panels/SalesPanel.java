package Panels;

import Objects.Client;
import Objects.Sales;
import Objects.property;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SalesPanel extends JDialog{
    private JTable Clients;
    private JTable Properties;
    private JTable salesTable;
    private JTextField id;
    private JTextField propertyid;
    private JTextField clientid;
    private JTextField price;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JTextField date;
    private JPanel salesPanel;

    public SalesPanel(JFrame parent) {
        super(parent);
        setTitle("Sales");
        setContentPane(salesPanel);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(parent);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int propertyidText = Integer.parseInt(propertyid.getText().trim());
                int clientidText = Integer.parseInt(clientid.getText().trim());
                String priceText = price.getText();
                String dateText = date.getText();
                if (propertyidText == 0 || clientidText == 0 || priceText.isEmpty() || dateText.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    Sales obj = new Sales();
                    if (obj.addSales(new Sales(0, propertyidText,clientidText,priceText, dateText))) {
                        JOptionPane.showMessageDialog(null, "Sale Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                        propertyid.setText("");
                        clientid.setText("");
                        price.setText("");
                        date.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Sale Not Added", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillSalesTable();
                fillClientTable();
                fillPropertyTable();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idText = Integer.parseInt(id.getText().trim());
                int propertyidText = Integer.parseInt(propertyid.getText().trim());
                int clientidText = Integer.parseInt(clientid.getText().trim());
                String priceText = price.getText();
                String dateText = date.getText();
                if (propertyidText == 0 || clientidText == 0 || priceText.isEmpty() || dateText.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    Sales obj = new Sales();
                    if (obj.editSales(new Sales(idText, propertyidText,clientidText,priceText, dateText))) {
                        JOptionPane.showMessageDialog(null, "Sale Edited", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Sale Not Edited", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillSalesTable();
                fillClientTable();
                fillPropertyTable();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = id.getText();
                Sales obj = new Sales();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nothing Selected", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.removeSales(Integer.parseInt(idText.trim()))) {
                        JOptionPane.showMessageDialog(null, "Sale Removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Sale Not Removed", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillSalesTable();
                fillClientTable();
                fillPropertyTable();
            }
        });

        Properties.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = Properties.getSelectedRow();
                propertyid.setText(Properties.getValueAt(rowIndex,0).toString());
            }
        });
        Clients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = Clients.getSelectedRow();
                clientid.setText(Clients.getValueAt(rowIndex,0).toString());
            }
        });
        salesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = salesTable.getSelectedRow();
                id.setText(salesTable.getValueAt(rowIndex, 0).toString());
                propertyid.setText(salesTable.getValueAt(rowIndex,1).toString());
                clientid.setText(salesTable.getValueAt(rowIndex,2).toString());
                price.setText(salesTable.getValueAt(rowIndex, 3).toString());
                date.setText(salesTable.getValueAt(rowIndex,4).toString());
            }
        });

        fillSalesTable();
        fillClientTable();
        fillPropertyTable();
        setVisible(true);
    }
    private void fillPropertyTable() {
        property obj = new property();
        ArrayList<property> propertyList = obj.propertyList();
        String[] col = {"ID", "Owner ID", "Price"};
        Object[][] rows = new Object[propertyList.size()][3];
        for (int i = 0; i < propertyList.size(); i++) {
            rows[i][0] = propertyList.get(i).getId();
            rows[i][1] = propertyList.get(i).getOwner();
            rows[i][2] = propertyList.get(i).getPrice();
        }
        DefaultTableModel model = new DefaultTableModel(rows, col);
        Properties.setModel(model);
        Properties.setRowHeight(30);
    }
    private void fillClientTable() {
        Client obj = new Client();
        ArrayList<Client> clientList = obj.clientList();
        String[] columns = {"ID","First Name", "Last Name"};
        Object[][] rows = new Object[clientList.size()][3];
        for (int i = 0; i < clientList.size(); i++) {
            rows[i][0] = clientList.get(i).getId();
            rows[i][1] = clientList.get(i).getFirstname();
            rows[i][2] = clientList.get(i).getLastname();
        }
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        Clients.setModel(model);
        Clients.setRowHeight(30);
    }

    private void fillSalesTable() {
        Sales obj = new Sales();
        ArrayList<Sales> salesList = obj.salesList();
        String[] col = {"ID", "Property", "Client", "Price", "Date"};
        Object[][] rows = new Object[salesList.size()][5];
        for (int i = 0; i < salesList.size(); i++) {
            rows[i][0] = salesList.get(i).getId();
            rows[i][1] = salesList.get(i).getPropertyid();
            rows[i][2] = salesList.get(i).getClientid();
            rows[i][3] = salesList.get(i).getPrice();
            rows[i][4] = salesList.get(i).getDate();
        }
        DefaultTableModel model = new DefaultTableModel(rows, col);
        salesTable.setModel(model);
        salesTable.setRowHeight(30);
    }
    public static void main(String[] args) {
        SalesPanel panel = new SalesPanel(null);
    }
}
