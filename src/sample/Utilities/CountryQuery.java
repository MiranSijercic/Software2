package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Country;
import java.sql.*;

/**
 * This Abstract Class is used to store CRUD methods for the Country table in the database.
 */
public abstract class CountryQuery {

    /**
     * This method runs a SQL select statement to get all Countries from the database
     * @return an Observable list of Countries
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String dbCountry = rs.getString("Country");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Country country = new Country(countryID, dbCountry, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allCountries.add(country);
        }

        return allCountries;
    }
}
