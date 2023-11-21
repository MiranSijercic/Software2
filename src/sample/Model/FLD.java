package sample.Model;

import java.sql.Timestamp;

/**
 * This Class constructs First Level Division (City/Province) Objects and contains all methods for manipulating FLD class fields.
 */
public class FLD {
    private int divisionID;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public FLD(int divisionID, String division, Timestamp createDate, String createdBy,
               Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * Gets the divisionID field
     * @return the Division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the divisionID field
     * @param divisionID the Division ID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the division field
     * @return the Division Name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division field
     * @param division the Division Name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the createDate field
     * @return the Date the FLD object was Created as a Timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate field
     * @param createDate the Date the FLD object was Created as a Timestamp to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the createdBy field
     * @return the User the FLD object was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy field
     * @param createdBy the User the FLD object was Created by to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the lastUpdate field
     * @return the Timestamp of the Last Update to any FLD fields
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the lastUpdate field
     * @param lastUpdate the Timestamp of the Last Update to any FLD field to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the lastUpdatedBy field
     * @return the User who made the latest update to any FLD field
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the lastUpdatedBy field
     * @param lastUpdatedBy the User who made the latest update to any FLD field to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the countryID field
     * @return the ID of the Country the FLD belongs to
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the countryID field
     * @param countryID the ID of the Country the FLD belongs to, to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Overrides toString method to print the FLD Name
     * @return the FLD Name as a String
     */
    @Override
    public String toString() {
        return ("" + division);
    }
}
