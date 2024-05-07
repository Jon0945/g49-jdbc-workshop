package se.lexicon.data;

import se.lexicon.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOJDBC implements CityDAO {
    private static final String FIND_BY_ID = "SELECT * FROM city WHERE ID = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM city WHERE CountryCode = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM city WHERE Name = ?";
    private static final String FIND_ALL = "SELECT * FROM city";
    private static final String ADD_CITY = "INSERT INTO city (Name,CountryCode,District,Population) VALUES(?,?,?,?)";
    private static final String UPDATE_CITY = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
    private static final String DELETE_CITY = "DELETE FROM city WHERE ID = ?";

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

    private PreparedStatement createFindAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL);
        return statement;
    }

    private PreparedStatement createAddCity(Connection connection, City newCity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_CITY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,newCity.getCityName());
        statement.setString(2,newCity.getCountryCode());
        statement.setString(3,newCity.getCityDistrict());
        statement.setInt(4,newCity.getCityPopulation());
        statement.executeUpdate();
        return statement;
    }

    private PreparedStatement createUpdateCity(Connection connection, City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_CITY);
        statement.setString(1,city.getCityName());
        statement.setString(2,city.getCountryCode());
        statement.setString(3,city.getCityDistrict());
        statement.setInt(4,city.getCityPopulation());
        statement.setInt(5,city.getCityId());
        return statement;
    }

    private PreparedStatement createDeleteCity(Connection connection,int cityId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_CITY);
        statement.setInt(1,cityId);
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
        List<City> result = new ArrayList<>();
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindAll(connection);
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
    public City add(City newCity) {
        if(newCity.getCityId() !=0) {
            return newCity;
        }
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createAddCity(connection,newCity);
                ResultSet resultSet = statement.getGeneratedKeys()
        ) {
            int cityId = 0;
            while (resultSet.next()) {
                cityId = resultSet.getInt(1);
            }

            newCity = new City(
                    cityId,
                    newCity.getCityName(),
                    newCity.getCountryCode(),
                    newCity.getCityDistrict(),
                    newCity.getCityPopulation()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCity;
    }
    @Override
    public City update(City city) throws IllegalArgumentException {
        if(city.getCityId() == 0) throw new IllegalArgumentException("Can not update. City doesn't exist");
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createUpdateCity(connection,city)
        ){
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city) {
        int result = 0;
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createDeleteCity(connection,city.getCityId())
        ){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
