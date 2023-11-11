package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public abstract class UtilityQueries {

    public static ObservableList<LocalTime> convertedTimes() {
        ZonedDateTime openingTimeEastern = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));

        ZonedDateTime openingTimeLocal = openingTimeEastern.withZoneSameInstant(ZoneId.systemDefault());
        int businessHourStart = openingTimeLocal.toLocalTime().getHour();

        ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
        for (int i = businessHourStart; i <= businessHourStart + 14; i++) {
            startTimes.add(LocalTime.of(i, 0));

        }
        return startTimes;
    }

    public static int monthAndTypeSelect (Month month, String type) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTH(Start) = Month(now()) AND YEAR(Start) = YEAR(now())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return -1;
    }

    public static ObservableList<Appointment> appointmentByCurrentWeekSelect () throws SQLException {
        ObservableList<Appointment> appointmentsByCurrentWeek = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE YEAR(Start) = YEAR(CURDATE()) AND WEEK(Start) = WEEK(CURDATE())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            appointmentsByCurrentWeek.add(appointment);
        }

        return appointmentsByCurrentWeek;
    }

}
