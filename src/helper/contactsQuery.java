package helper;

import model.Contact;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class contactsQuery {
    /**
     * Loads contact records from database into java objects
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int rowsAffected = 0;
        while(rs.next()){
            Integer ContactId = rs.getInt("Contact_ID");
            String ContactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact newContact = new Contact(ContactId, ContactName, email);
            Session.addContact(newContact);
            //System.out.println("Contact added: " + ContactName);
            rowsAffected = rowsAffected + 1;
        }
        return rowsAffected;
    }

    /**
     * Creates new contact record in database
     * @param newContact the Customer object inserted
     * @return the number of records that were inserted
     */
    public static int insert(Contact newContact) throws SQLException {
        String sql = "INSERT INTO CONTACTS (Contact_ID, Contact_Name, Email) VALUES(?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newContact.getContactId());
        ps.setString(2, newContact.getContactName());
        ps.setString(3, newContact.getEmail());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * Updates contact record in database
     * @param newContact the Customer object updated
     * @return the number of records that were affected
     */
    public static int update(Contact newContact) throws SQLException {
        String sql = "UPDATE CONTACTS SET CONTACT_NAME = ?, Email = ? WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, newContact.getContactName());
        ps.setString(2, newContact.getEmail());
        ps.setInt(3, newContact.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes contact record from database
     * @param contactId the Customer ID to search for
     * @return the number of records that were deleted
     */
    public static int delete(int contactId) throws SQLException {
        String sql = "DELETE FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes contact record from database
     * @param newContact the Customer object to delete
     * @return the number of records that were deleted
     */
    public static int delete(Contact newContact) throws SQLException {
        String sql = "DELETE FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newContact.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}