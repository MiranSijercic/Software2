package sample.Model;

/**
 * This Class constructs Contact Objects and contains all methods for manipulating Contact class fields.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Gets the contactID field
     * @return the Contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contactID field
     * @param contactID the Contact ID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the contactName field
     * @return the Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contactName field
     * @param contactName the Contact Name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the email field
     * @return the Contact's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email field
     * @param email the Contact's email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Overrides toString method to print the Contact Name
     * @return the Contact Name as a String
     */
    @Override
    public String toString() { return ("" + contactName);}

}
