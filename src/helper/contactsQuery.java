package helper;

import model.Contact;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database helper class for contact objects
 */

public abstract class contactsQuery {
    /**
     * Loads contact records from database into java objects
     *
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int rowsAffected = 0;
        while (rs.next()) {
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
}