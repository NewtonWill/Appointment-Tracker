package main;

import helper.customersQuery;
import helper.usersQuery;
import model.Customer;
import model.Session;

import java.sql.SQLException;

public class TestScripts {

    public static void loginTest() throws SQLException {
        System.out.print("Login test 1: pass expected -> ");
        usersQuery.checkMatch("test", "test");
        System.out.print("Login test 2: fail expected -> ");
        usersQuery.checkMatch("This will", "fail");
        System.out.print("Login test 1: pass expected -> ");
        usersQuery.checkMatch("admin", "admin");
    }

    public static void customerListTest() throws SQLException{

    }

    public static void customerAddTest() throws SQLException{
        Customer testCustomer1 = new Customer(666, "Joe Tester", "1001 Main Street",
                "76131", "888-867-5309", 42);

        try {
            customersQuery.insert(testCustomer1);
            Session.addCustomer(testCustomer1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
