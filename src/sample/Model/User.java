package sample.Model;

import java.sql.Timestamp;

/**
 * This Class constructs User Objects and contains all methods for manipulating User class fields.
 */
public class User {
    private int userID;
    private String userName;
    private String password;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public User(int userID, String userName, String password, Timestamp createDate, String createdBy,
                Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the userID field
     * @return the User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the userID field
     * @param userID the User ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the userName field
     * @return the User's Username for login
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName field
     * @param userName the User's Username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password field
     * @return the User's Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password field
     * @param password the User's Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the createDate field
     * @return the Date the User object was Created as a Timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate field
     * @param createDate the Date the User object was Created as a Timestamp to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the createdBy field
     * @return the existing User the new User object was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy field
     * @param createdBy the existing User the new User object was Created by to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the lastUpdate field
     * @return the Timestamp of the Last Update to any User fields
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the lastUpdate field
     * @param lastUpdate the Timestamp of the Last Update to any User field to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the lastUpdatedBy field
     * @return the existing User who made the latest update to any User field
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the lastUpdatedBy field
     * @param lastUpdatedBy the exist User who made the latest update to any user field to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Overrides toString method to print the User's UserName
     * @return the Username as a String
     */
    @Override
    public String toString() {
        return ("" + userName);
    }
}
