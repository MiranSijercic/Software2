package sample.Model;

import java.sql.Timestamp;

/**
 * This Class constructs Customer Objects and contains all methods for manipulating Customer class fields.
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    Timestamp createDate, String createdBy, Timestamp lastUpdate,
                    String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * Gets the customerID field
     * @return the Customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customerID field
     * @param customerID the Customer ID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the customerName field
     * @return the Customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customerName field
     * @param customerName the Customer Name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the address field
     * @return the Customer Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address field
     * @param address the Customer Address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the postalCode field
     * @return the Customer Postal Code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postalCode field
     * @param postalCode the Customer Postal Code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone field
     * @return the Customer Phone Number as a String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone field
     * @param phone the Customer Phone Number as a String to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the createDate field
     * @return the Date the Customer object was Created as a Timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate field
     * @param createDate the Date the Customer object was Created as a Timestamp to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the createdBy field
     * @return the User the Customer object was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy field
     * @param createdBy the User the Customer object was Created by to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the lastUpdate field
     * @return the Timestamp of the Last Update to any Customer fields
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the lastUpdate field
     * @param lastUpdate the Timestamp of the Last Update to any Customer field to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the lastUpdatedBy field
     * @return the User who made the latest update to any Customer field
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the lastUpdatedBy field
     * @param lastUpdatedBy the User who made the latest update to any Customer field to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the divisionID field
     * @return the ID of the State or Province of the Customer's address
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the divisionID field
     * @param divisionID the ID of the State or Province of the Customer's address to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Overrides toString method to print the Customer Name
     * @return the Customer Name as a String
     */
    @Override
    public String toString() {
        return ("" + customerName);
    }
}
