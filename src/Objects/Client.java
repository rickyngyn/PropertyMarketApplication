package Objects;

import database.dbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private String number;

    public Client() {

    }
    public Client(int id, String firstname, String lastname, String number) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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

    public boolean addClient(Client obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("INSERT INTO personalclientlist (firstname, lastname, number) VALUES (?,?,?)");
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getNumber());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editClient(Client obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("UPDATE personalclientlist SET firstname = ?, lastname = ?, number = ? WHERE id = ?");
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getNumber());
            ps.setInt(4, obj.getId());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeClient(int id) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("DELETE FROM personalclientlist WHERE id = ?");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Client> clientList() {
        ArrayList<Client> list = new ArrayList<>();

        Statement st;
        ResultSet rs;
        try {
            st = dbConnection.getConection().createStatement();
            rs = st.executeQuery("SELECT * FROM personalclientlist");

            Client obj;
            while (rs.next()) {
                obj = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
