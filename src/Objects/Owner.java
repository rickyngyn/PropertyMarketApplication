package Objects;

import database.dbConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Owner {
    private int id;
    private String firstname;
    private String lastname;
    private String number;

    public Owner() {
    }

    public Owner(int id, String firstname, String lastname, String number) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNumber() {
        return number;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public boolean addOwner(Owner obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("INSERT INTO personalownerslist (firstname, lastname, number)" + "VALUES (?, ?, ?)");
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getNumber());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editOwner(Owner obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("UPDATE personalownerslist SET firstname = ?, lastname = ?, number = ? WHERE id = ?");
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getNumber());
            ps.setInt(4, obj.getId());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeOwner(int id) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("DELETE FROM personalownerslist WHERE id = ?");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Owner> ownerList() {
        ArrayList<Owner> list = new ArrayList<>();

        Statement st;
        ResultSet rs;
        try {
            st = dbConnection.getConection().createStatement();
            rs = st.executeQuery("SELECT * FROM personalownerslist");

            Owner obj;
            while (rs.next()) {
                obj = new Owner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
