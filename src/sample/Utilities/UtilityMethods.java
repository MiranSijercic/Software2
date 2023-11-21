package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public abstract class UtilityMethods {

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

    public static int monthAndTypeSelect (String type, int month) throws SQLException {
        String sql = "SELECT COUNT(*) as row_count FROM appointments WHERE Type = ? AND MONTH(Start) = ? AND YEAR(Start) = YEAR(now())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("row_count");
            return count;
        }

        return 0;
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

    public static int userAndMonthSelect (int userID, int month) throws SQLException {
        String sql = "SELECT COUNT(*) as row_count FROM appointments WHERE User_ID = ? AND MONTH(Start) = ? AND YEAR(Start) = YEAR(now())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("row_count");
            return count;
        }

        return 0;
    }

    public static ObservableList<Appointment> contactScheduleSelect (int contactID) throws SQLException {
        ObservableList<Appointment> contactWeekSchedule = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ? AND Start >= NOW()";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contactID);
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
            int contact_id = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contact_id);
            contactWeekSchedule.add(appointment);
        }
        return contactWeekSchedule;
    }

    public static void logToFile(String message) throws IOException {
        File logFile = new File("login_activity.txt");
        FileWriter fileWriter = new FileWriter(logFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(message);
        printWriter.close();
    }

}
