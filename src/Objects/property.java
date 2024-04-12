package Objects;

import database.dbConnection;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class property {
    private int id;
    private String type;
    private int owner;
    private String price;
    private String address;
    private int bedrooms;
    private int bathrooms;
    private String description;
    private String squarefeet;

    public property() {

    }

    public property(int id, String type, int owner, String price, String address, int bedrooms, int bathrooms, String description, String squarefeet) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.price = price;
        this.address = address;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.squarefeet = squarefeet;
    }
    public void setSquarefeet(String squarefeet) {
        this.squarefeet = squarefeet;
    }

    public String getSquarefeet() {
        return squarefeet;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getOwner() {
        return owner;
    }

    public String getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public String getDescription() {
        return description;
    }

    public boolean addPropertyImage(int propertyId, String path) {
        PreparedStatement ps;
        try {
            try {
                FileInputStream propertyImage = new FileInputStream(new File(path));
                ps = dbConnection.getConection().prepareStatement("INSERT INTO propertyimages (propertyid, image) VALUES (?,?)");
                ps.setInt(1, propertyId);
                ps.setBinaryStream(2, propertyImage);
                return (ps.executeUpdate() > 0);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addNewProperty(property obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("INSERT INTO properties (type, owner, squarefeet, price, address, bedrooms, bathrooms, description) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, obj.getType());
            ps.setInt(2, obj.getOwner());
            ps.setString(3, obj.getSquarefeet());
            ps.setString(4, obj.getPrice());
            ps.setString(5, obj.getAddress());
            ps.setInt(6, obj.getBedrooms());
            ps.setInt(7, obj.getBathrooms());
            ps.setString(8, obj.getDescription());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean editProperty(property obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("UPDATE properties SET type = ?, owner = ?, squarefeet = ?, price = ?, address = ?, bedrooms = ?, bathrooms = ?, description = ? WHERE id = ?");
            ps.setString(1, obj.getType());
            ps.setInt(2, obj.getOwner());
            ps.setString(3, obj.getSquarefeet());
            ps.setString(4, obj.getPrice());
            ps.setString(5, obj.getAddress());
            ps.setInt(6, obj.getBedrooms());
            ps.setInt(7, obj.getBathrooms());
            ps.setString(8, obj.getDescription());
            ps.setInt(9, obj.getId());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean removeProperty(int id) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("DELETE FROM properties WHERE id = ?");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList propertyList() {
        ArrayList<property> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try {
            st = dbConnection.getConection().createStatement();
            rs = st.executeQuery("SELECT * FROM properties");

            property obj;
            while (rs.next()) {
                obj = new property(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9));
                list.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public HashMap<byte[], Integer> fillJlist(int propertyId) {
        HashMap<byte[], Integer> list = new HashMap<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = dbConnection.getConection().prepareStatement("SELECT * FROM propertyimages WHERE propertyid = ?");
            ps.setInt(1, propertyId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.put(rs.getBytes("image"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public byte[] getImageById(int imageId) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = dbConnection.getConection().prepareStatement("SELECT image FROM propertyimages WHERE id = ?");
            ps.setInt(1, imageId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBytes("image");
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean removeImage(int imageId) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("DELETE FROM propertyimages WHERE id = ?");
            ps.setInt(1, imageId);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
