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

/**
 * This is an Abstract class containing utility methods used for generating reports, converting timezones, and sql statements for filtering lists
 */
public abstract class UtilityMethods {

    /**
     * This method is used to convert Business hours from US Eastern Time to the System Local Time.
     * @return An Observable list of LocalTime that will be used to populate combo boxes in the Appointment scheduling areas of the application
     */
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

    /**
     * This method is used to query the database to generate the report for Appointments by Month and Type
     * @param type is the Appointment type that the user will select in the gui in the Appointment By Type Screen
     * @param month is the Month that the user will select in the gui in the Appointment by Type Screen
     * @return the count of Appointments
     * @throws SQLException to query database
     */
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

    /**
     * This method runs a sql query used to sort all Appointments by the current week. This is called in sortedAppointmentController
     * @return An Observable list of Appointments that will be used to populate the Tableview in the Sorted Appointments Screen
     * @throws SQLException to query the database
     */
    public static ObservableList<Appointment> appointmentByCurrentWeekSelect () throws SQLException {
        ObservableList<Appointment> appointmentsByCurrentWeek = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";
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

    /**
     * This method is used to query the database to generate the report for the count of Appointments created by selected User by selected month
     * @param userID is the User ID that the Application user will select in the gui in the User Report Screen
     * @param month is the Month that the user will select in the gui in the User Report Screen
     * @return the count of Appointments
     * @throws SQLException to query database
     */
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

    /**
     * This method runs a sql query used to filter all Appointments by a selected Contact. This is called in contactScheduleController
     * @param contactID is the Contact ID that the user will select in the gui in the Contact Schedule screen
     * @return An Observable list of Appointments that will be used to populate the Tableview in contactScheduleController
     * @throws SQLException to query the database
     */
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

    /**
     * This method logs login attempts to the login_activity.txt file
     * @param message will write to a new line each time a login is attempted. Will log attempt time, and if successful, the User ID of the account logging in
     * @throws IOException to write to the file
     */
    public static void logToFile(String message) throws IOException {
        File logFile = new File("login_activity.txt");
        FileWriter fileWriter = new FileWriter(logFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(message);
        printWriter.close();
    }

}
