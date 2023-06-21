package helper;

import model.Customer;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class customersQuery {
    public static void loadToMemory() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Integer divisionId = rs.getInt("Division_ID");

            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            Session.addCustomer(newCustomer);
            System.out.println("Customer added: " + customerName);
        }
    }
}
