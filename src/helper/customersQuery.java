package helper;

import model.Customer;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

/**
 * Database helper class for customer objects
 */
public abstract class customersQuery {
    /**
     * Loads customer records from database into java objects
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int rowsAffected = 0;
        while(rs.next()){
            Integer customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Integer divisionId = rs.getInt("Division_ID");

            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            Session.addCustomer(newCustomer);
            //System.out.println("Customer added: " + customerName);
            rowsAffected = rowsAffected + 1;
        }
        return rowsAffected;
    }

    /**
     * Creates new customer record in database
     * @param newCustomer the Customer object inserted
     * @return the number of records that were inserted
     */
    public static int insert(Customer newCustomer) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newCustomer.getCustomerId());
        ps.setString(2, newCustomer.getName());
        ps.setString(3, newCustomer.getAddress());
        ps.setString(4, newCustomer.getPostalCode());
        ps.setString(5, newCustomer.getPhone());
        ps.setInt(6, newCustomer.getDivisionId());
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, "User " + Session.getLocalUserId());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(10, "User " + Session.getLocalUserId());
        return ps.executeUpdate();
    }

    /**
     * Updates customer record in database
     * @param newCustomer the Customer object updated
     * @return the number of records that were affected
     */
    public static int update(Customer newCustomer) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?," +
                "Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, newCustomer.getName());
        ps.setString(2, newCustomer.getAddress());
        ps.setString(3, newCustomer.getPostalCode());
        ps.setString(4, newCustomer.getPhone());
        ps.setInt(5, newCustomer.getDivisionId());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, "User " + Session.getLocalUserId());
        ps.setInt(8, newCustomer.getCustomerId());
        return ps.executeUpdate();
    }

    /**
     * Deletes customer record from database
     * @param customerId the Customer ID to search for
     * @return the number of records that were deleted
     */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }

    /**
     * Deletes customer record from database
     * @param newCustomer the Customer object to delete
     * @return the number of records that were deleted
     */
    public static int delete(Customer newCustomer) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newCustomer.getCustomerId());
        return ps.executeUpdate();
    }
}
