package se.lexicon.data;

import se.lexicon.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAOJDBC implements CityDAO {
    private static final String FIND_BY_ID = "SELECT * FROM city WHERE ID = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM city WHERE CountryCode = ?";

    private static final String FIND_BY_NAME = "SELECT * FROM city WHERE Name = ?";

    private PreparedStatement createFindById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
        return statement;
    }

    private PreparedStatement createFindByCode(Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE);
        statement.setString(1,code);
        return statement;
    }

    private PreparedStatement createFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name);
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
    @Override
    public List<City> findByCode(String code) {
        List<City> result = new ArrayList<>();
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindByCode(connection,code);
                ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()) {
                result.add(cityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<City> findByName(String name) {
        List<City> result = new ArrayList<>();
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindByName(connection,name);
                ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()) {
                result.add(cityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<City> findAll() {
        return null;
    }

    @Override
    public City add(City city) {
        return null;
    }
    @Override
    public City update(City city) {
        return null;
    }

    @Override
    public int delete(City city) {
        return 0;
    }

}
