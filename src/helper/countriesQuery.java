package helper;

import model.Country;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countriesQuery {
    public static void loadToMemory() throws SQLException {
    String sql = "SELECT * FROM Countries";
    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
        Integer CountryId = rs.getInt("Country_ID");
        String CountryName = rs.getString("Country");

        Country newCountry = new Country(CountryId, CountryName);
        Session.addCountry(newCountry);
        System.out.println("Country added: " + CountryName);
    }
}
}
