package helper;

import model.Country;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database helper class for country objects
 */
public abstract class countriesQuery {

    /**
     * Loads country records from database into java objects
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
    String sql = "SELECT * FROM Countries";
    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    int rowsAffected = 0;
    while(rs.next()){
        Integer CountryId = rs.getInt("Country_ID");
        String CountryName = rs.getString("Country");

        Country newCountry = new Country(CountryId, CountryName);
        Session.addCountry(newCountry);
        //System.out.println("Country added: " + CountryName);
        rowsAffected = rowsAffected + 1;
        }
    return rowsAffected;
    }
}
