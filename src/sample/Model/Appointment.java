package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This Class constructs Appointment Objects and contains all methods for manipulating Appointment class fields.
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointment(int appointmentID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                       String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Gets the appointmentID field
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointmentID field
     * @param appointmentID the Appointment ID to set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the title field
     * @return the Appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title field
     * @param title the Appointment Title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description field
     * @return the Appointment Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description field
     * @param description the Appointment Description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location field
     * @return the Appointment Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location field
     * @param location the Appointment Location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the type field
     * @return the Appointment Type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type field
     * @param type the Appointment Type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start field
     * @return the Appointment Start time as a Timestamp
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Sets the start field
     * @param start the Appointment Start time as a Timestamp to set
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Gets the end field
     * @return the Appointment End time as a Timestamp
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Sets the end field
     * @param end the Appointment End Time as a Timestamp to set
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Gets the createDate field
     * @return the Date the Appointment was Created as a Timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate field
     * @param createDate the Date the Appointment was Created as a Timestamp to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the createdBy field
     * @return the User the Appointment was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy field
     * @param createdBy the User the Appointment was Created by to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the lastUpdate field
     * @return the Timestamp of the Last Update to any Appointment fields
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the lastUpdate field
     * @param lastUpdate the Timestamp of the Last Update to any Appointment field to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the lastUpdatedBy field
     * @return the User who made the latest update to any Appointment field
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the lastUpdatedBy field
     * @param lastUpdatedBy the User who made the latest update to any Appointment field to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the customerID field
     * @return the ID number of the Customer the Appointment is scheduled for
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customerID field
     * @param customerID the ID number of the Customer the Appointment is scheduled for to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the userID field
     * @return the ID number of the User that created the Appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the userID field
     * @param userID the ID number of the User that created the Appointment to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the contactID field
     * @return the ID number of the Contact associated with the Appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contactID field
     * @param contactID the ID number of the Contact associated with the Appointment to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}

