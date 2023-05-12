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

    public static boolean checkMatch(String usernameIn, String passwordIn) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String usernameDb = rs.getString("User_Name");
            String passwordDb = rs.getString("Password");
            if(Objects.equals(usernameDb, usernameIn) && Objects.equals(passwordDb, passwordIn)){
                System.out.println("PASS");
                return true;
            }
        }
        System.out.println("FAIL");
        return false;
    }
}
