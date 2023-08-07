package sample.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AppointmentQuery {

    public static int insert(String title, String description, String location, String type,
                             Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, start);
        ps.setTimestamp(7, end);
        ps.setTimestamp(8, createDate);
        ps.setString(9, createdBy);
        ps.setTimestamp(10, lastUpdate);
        ps.setString(11, lastUpdatedBy);
        ps.setInt(12, customerID);
        ps.setInt(13, userID);
        ps.setInt(14, contactID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int appointmentID, String title, String description, String location, String type,
                             Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, " +
                "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, start);
        ps.setTimestamp(7, end);
        ps.setTimestamp(8, createDate);
        ps.setString(9, createdBy);
        ps.setTimestamp(10, lastUpdate);
        ps.setString(11, lastUpdatedBy);
        ps.setInt(12, customerID);
        ps.setInt(13, userID);
        ps.setInt(14, contactID);
        ps.setInt(15, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
