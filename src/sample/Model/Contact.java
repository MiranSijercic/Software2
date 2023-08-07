package sample.Model;

public class Contact {
    private int contactID;
    private String ContactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        ContactName = contactName;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
