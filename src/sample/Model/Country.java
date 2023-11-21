package sample.Model;

import java.sql.Timestamp;

/**
 * This Class constructs Country Objects and contains all methods for manipulating Country class fields.
 */
public class Country {
    private int countryID;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;


    public Country(int countryID, String country, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the countryID field
     * @return the Country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the countryID field
     * @param countryID the Country ID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the country field
     * @return the Country Name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country field
     * @param country the Country Name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the createDate field
     * @return the Timestamp of the Date the Country was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate field
     * @param createDate the Timestamp of the Date the Country was created to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the createBy field
     * @return the User the Country was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createBy field
     * @param createdBy the User the Country was created by to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the lastUpdate field
     * @return the Timestamp of the Last Update to any Country fields
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the lastUpdate field
     * @param lastUpdate the Timestamp of the Last Update to any Country field to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the lastUpdatedBy field
     * @return the User who made the latest update to any Country fields
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the lastUpdatedBy field
     * @param lastUpdatedBy the User who made the latest update to any Country field to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Overrides toString method to print the Country Name
     * @return the Country Name as a String
     */
    @Override
    public String toString() {
        return ("" + country);
    }
}
