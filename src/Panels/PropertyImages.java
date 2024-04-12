package Panels;

import Objects.property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PropertyImages extends JDialog {
    private JPanel panel;
    private JTable table1;
    private JTextField searchText;
    private JButton browseButton;
    private JLabel propertyimg;
    private JList list1;
    private JButton showButton;
    private JButton addButton;
    private JButton removeButton;
    String propImagePath = "";
    int propertyId = 0;

    public PropertyImages(JFrame parent) {
        super(parent);
        setTitle("Property Images");
        setContentPane(panel);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(parent);
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<>(model);
                table1.setRowSorter(model1);
                model1.setRowFilter(RowFilter.regexFilter(searchText.getText()));
            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // check computer for images
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Image");
                fileChooser.setCurrentDirectory(new File("C:\\"));

                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Images",".jpg", ".png", ".jpeg");
                fileChooser.addChoosableFileFilter(fileFilter);
                int fileState = fileChooser.showSaveDialog(null);
                if (fileState == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    propertyimg.setIcon(resize(path, null));
                    propImagePath = path;

                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // add images to property
                property obj = new property();
                if (propertyId != 0) {
                    if (obj.addPropertyImage(propertyId, propImagePath.trim())) {
                        JOptionPane.showMessageDialog(null, "Image Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Image Not Added", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Select Property", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
                fillList();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // remove slected image from property
                try {
                    String selectedItem = String.valueOf(list1.getSelectedValue());
                    Integer imageId = Integer.valueOf(selectedItem);
                    if (new property().removeImage(imageId)) {
                        JOptionPane.showMessageDialog(null, "Image Removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Image not Removed", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Select an Image", "Failed", JOptionPane.ERROR_MESSAGE);
                }
                fillList();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //get selected property id
                int rowIndex = table1.getSelectedRow();
                propertyId = Integer.valueOf(table1.getValueAt(rowIndex, 0).toString());

                fillList();
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedItem = String.valueOf(list1.getSelectedValue());
                Integer imageId = Integer.valueOf(selectedItem);
                byte[] image = new property().getImageById(imageId);
                propertyimg.setIcon(resize(null, image));
            }
        });
        fillList();
        fillJtable();
        setVisible(true);
    }
    public void fillList() {
        HashMap<byte[], Integer> imageListMap = new property().fillJlist(propertyId);
        DefaultListModel listModel = new DefaultListModel();
        int i = 0;
        for (Integer id: imageListMap.values()) {
            listModel.add(i, id);
            i++;
        }
        list1.setModel(listModel);
    }
    private void fillJtable() {
        property obj = new property();
        ArrayList<property> propertyList = obj.propertyList();
        String[] col = {"ID", "Type","Address"};
        Object[][] rows = new Object[propertyList.size()][3];
        for (int i = 0; i < propertyList.size(); i++) {
            rows[i][0] = propertyList.get(i).getId();
            rows[i][1] = propertyList.get(i).getType();
            rows[i][2] = propertyList.get(i).getAddress();
        }
        DefaultTableModel model = new DefaultTableModel(rows, col);
        table1.setModel(model);
        table1.setRowHeight(25);
    }
    public ImageIcon resize(String path, byte[] theImage) {
        // method to resize image
        ImageIcon pic;
        if (theImage != null) {
            pic = new ImageIcon(theImage);
        }
        else {
            pic = new ImageIcon(path);
        }
        Image img = pic.getImage().getScaledInstance(propertyimg.getWidth(), propertyimg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img);
        return image;
    }
    public static void main(String[] args) {
        PropertyImages panel = new PropertyImages(null);
    }
}
