package se.lexicon.data;

import se.lexicon.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAOJDBC implements CityDAO {
    private static final String FIND_BY_ID = "SELECT * FROM city WHERE ID = ?";

    private PreparedStatement createFindById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
        return statement;
    }
    private City cityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(
                resultSet.getInt("ID"),
                resultSet.getString("Name"),
                resultSet.getString("CountryCode"),
                resultSet.getString("District"),
                resultSet.getInt("Population")
        );
    }

    @Override
    public City findById(int cityId) {
        City found = null;
        //Create database connection
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindById(connection, cityId);
                ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()) {
                found = cityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(found == null) {
            System.out.println("City not found");
        }
        return found;
    }

}
