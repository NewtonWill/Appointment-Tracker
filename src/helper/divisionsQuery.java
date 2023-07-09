package helper;

import model.Division;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database helper class for division objects
 */
public abstract class divisionsQuery {

    /**
     * Loads division records from database into java objects
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int rowsAffected = 0;
        while(rs.next()){
            Integer divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            Integer countryId = rs.getInt("Country_ID");

            Division newDivision = new Division(divisionId, divisionName, countryId);
            Session.addDivision(newDivision);
            //System.out.println("Division added: " + divisionName);
            rowsAffected = rowsAffected + 1;
        }
        return rowsAffected;
    }
}