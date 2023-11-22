package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointment;
import sample.Model.FLD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This Abstract Class is used to store CRUD methods for the FLD table in the database.
 */
public abstract class FLDQuery {

    /**
     * This method runs a SQL select statement to get all FLD from the database
     * @return an Observable list of States/Territories
     */
    public static ObservableList<FLD> getAllFLD() throws SQLException {
        ObservableList<FLD> allFLD = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");

            FLD fld = new FLD(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
            allFLD.add(fld);
        }

        return allFLD;
    }
}
