package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class usersQuery {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            System.out.print(userId + " | ");
            System.out.print(userName + "\n");
        }
    }

    /**
     * Method checks if log in is valid and returns validated user ID
     * @param usernameIn the username to log in with
     * @param passwordIn the password to log in with
     * @return the user ID that matches the successful username and password combo. Returns null if login fails
     */
    public static Integer checkMatch(String usernameIn, String passwordIn) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String usernameDb = rs.getString("User_Name");
            String passwordDb = rs.getString("Password");
            Integer userIdDb = rs.getInt("User_ID");
            if(Objects.equals(usernameDb, usernameIn) && Objects.equals(passwordDb, passwordIn)){
                System.out.println("PASS");
                return userIdDb;
            }
        }
        System.out.println("FAIL");
        return null;
    }
}
