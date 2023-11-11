package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class UserQuery {

    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            User user = new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allUsers.add(user);
        }
        return allUsers;
    }

    public static boolean foundUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String dbUserName = rs.getString("User_Name");
            String dbPassword = rs.getString("Password");

            if (dbUserName.equals(userName) && dbPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }

}
