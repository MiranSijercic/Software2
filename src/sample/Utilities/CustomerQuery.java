package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointment;
import sample.Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This Abstract Class is used to store CRUD methods for the Customer table in the database.
 */
public abstract class CustomerQuery {

    /**
     * This method runs a SQL insert statement when provided Customer Class field parameters to add a new Customer to the database
     */
    public static int insert(String customerName, String address, String postalCode, String phone,
                             Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By," +
                "Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method runs a SQL update statement when provided Customer Class field parameters to update an existing Customer in the database
     */
    public static int update(int customerID, String customerName, String address, String postalCode, String phone,
                             Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?," +
                "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5,createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.setInt(10, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method runs a SQL delete statement to remove a Customer with the provided customerID parameter from the database
     */
    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method runs a SQL select statement to get all Customers from the database
     * @return an Observable list of Customers
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");

            Customer customer = new Customer(customerID, customerName, address, postalCode, phone, createDate,
                    createdBy, lastUpdate, lastUpdatedBy, divisionID);
            allCustomers.add(customer);
        }

        return allCustomers;
    }
}
