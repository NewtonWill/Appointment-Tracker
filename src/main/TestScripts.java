package main;

import helper.FruitsQuery;
import helper.usersQuery;

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

    public static void fruitsTest() throws SQLException{
        FruitsQuery.select(3);
        int rowsAffected = FruitsQuery.delete(7);
        System.out.print("Rows Affected: " + rowsAffected + "\n");
    }

    public static void customerListTest() throws SQLException{

    }



}
