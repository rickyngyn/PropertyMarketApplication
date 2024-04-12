package Objects;

import database.dbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Sales {
    private int id;
    private int propertyid;
    private int clientid;
    private String price;
    private String date;

    public Sales() {
    }

    public Sales(int id, int propertyid, int clientid, String price, String date) {
        this.id = id;
        this.propertyid = propertyid;
        this.clientid = clientid;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getPropertyid() {
        return propertyid;
    }

    public int getClientid() {
        return clientid;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public boolean addSales(Sales obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("INSERT INTO sales (propertyid, clientid, price, date) VALUES (?,?,?,?)");
            ps.setInt(1, obj.getPropertyid());
            ps.setInt(2, obj.getClientid());
            ps.setString(3, obj.getPrice());
            ps.setString(4, obj.getDate());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean editSales(Sales obj) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("UPDATE sales SET propertyid = ?, clientid = ?, price = ?, date = ? WHERE id = ?");
            ps.setInt(1, obj.getPropertyid());
            ps.setInt(2, obj.getClientid());
            ps.setString(3, obj.getPrice());
            ps.setString(4, obj.getDate());
            ps.setInt(5, obj.getId());
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean removeSales(int id) {
        PreparedStatement ps;
        try {
            ps = dbConnection.getConection().prepareStatement("DELETE FROM sales WHERE id = ?");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList salesList() {
        ArrayList<Sales> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try {
            st = dbConnection.getConection().createStatement();
            rs = st.executeQuery("SELECT * FROM sales");

            Sales obj;
            while (rs.next()) {
                obj = new Sales(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                list.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
