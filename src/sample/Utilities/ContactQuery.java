package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointment;
import sample.Model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This Abstract Class is used to store CRUD methods for the Contact table in the database.
 */
public abstract class ContactQuery {

    /**
     * This method runs a SQL select statement to get all Contacts from the database
     * @return an Observable list of Contacts
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, email);
            allContacts.add(contact);
        }
        return allContacts;
    }
}
