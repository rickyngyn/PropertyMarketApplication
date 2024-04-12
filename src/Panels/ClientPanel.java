package Panels;

import Objects.Client;
import Objects.Owner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClientPanel extends JDialog{
    private JPanel clientPanel;
    private JScrollPane clientTable;
    private JTable table1;
    private JTextField idText;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JTextField textSearch;

    public ClientPanel(JFrame parent) {
        super (parent);
        setTitle("Clients");
        setContentPane(clientPanel);
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
                    firstNameText.setText("");
                    lastNameText.setText("");
                    phoneNumberText.setText("");
                    return;
                }
                else {
                    Client obj = new Client();
                    if (obj.addClient(new Client(0,firstname, lastname, number))) {
                        JOptionPane.showMessageDialog(null, "Success", "Added Client", JOptionPane.INFORMATION_MESSAGE);
                        firstNameText.setText("");
                        lastNameText.setText("");
                        phoneNumberText.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Client not added", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithClients();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText();
                Client obj = new Client();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.removeClient(Integer.parseInt(id))) {
                        JOptionPane.showMessageDialog(null, "Success", "Removed Client", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Client not removed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithClients();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idText.getText());
                String firstname = firstNameText.getText();
                String lastname = lastNameText.getText();
                String number = phoneNumberText.getText();
                Client obj = new Client();
                if ( firstname.isEmpty() || lastname.isEmpty() || number.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missing Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if (obj.editClient(new Client(id,firstname, lastname, number))) {
                        JOptionPane.showMessageDialog(null, "Success", "Edited Owner", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed", "Owner not edited", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fillJtableWithClients();
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
        fillJtableWithClients();
        setVisible(true);
    }

    private void fillJtableWithClients() {
        Client obj = new Client();
        ArrayList<Client> clientList = obj.clientList();
        String[] columns = {"ID","First Name", "Last Name", "Phone"};
        Object[][] rows = new Object[clientList.size()][4];
        for (int i = 0; i < clientList.size(); i++) {
            rows[i][0] = clientList.get(i).getId();
            rows[i][1] = clientList.get(i).getFirstname();
            rows[i][2] = clientList.get(i).getLastname();
            rows[i][3] = clientList.get(i).getNumber();
        }
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        table1.setModel(model);
        table1.setRowHeight(30);
    }

    public static void main(String[] args) {
        ClientPanel panel = new ClientPanel(null);
    }
}

