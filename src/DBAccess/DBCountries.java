package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*public class DBCountries {

    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

}*/
