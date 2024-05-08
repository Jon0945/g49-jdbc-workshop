package se.lexicon.data;

import se.lexicon.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOJDBC implements CityDAO {
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
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.FIND_BY_ID)
        ){
            statement.setInt(1, cityId);
            try (
                    ResultSet resultSet = statement.executeQuery()
            ){
                while (resultSet.next()) {
                    found = cityFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (found == null) {
            System.out.println("City not found");
        }
        return found;
    }
    @Override
    public List<City> findByCode(String code) {
        List<City> result = new ArrayList<>();
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.FIND_BY_CODE)
        ){
            statement.setString(1, code);
            try (
                    ResultSet resultSet = statement.executeQuery()

            ) {
                while (resultSet.next()) {
                    result.add(cityFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<City> findByName(String name) {
        List<City> result = new ArrayList<>();
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.FIND_BY_NAME)
        ){
            statement.setString(1, name);
            try (
                    ResultSet resultSet = statement.executeQuery()
            ){
                while (resultSet.next()) {
                    result.add(cityFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<City> findAll() {
        List<City> result = new ArrayList<>();
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.FIND_ALL)
        ){
            try(
                    ResultSet resultSet = statement.executeQuery()
            ){
                while (resultSet.next()) {
                    result.add(cityFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public City add(City newCity) {
        if (newCity.getCityId() != 0) {
            return newCity;
        }
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.ADD_CITY,
                        Statement.RETURN_GENERATED_KEYS)
        ){
            statement.setString(1, newCity.getCityName());
            statement.setString(2, newCity.getCountryCode());
            statement.setString(3, newCity.getCityDistrict());
            statement.setInt(4, newCity.getCityPopulation());
            statement.executeUpdate();
            try(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCity;
    }
    @Override
    public City update(City city) throws IllegalArgumentException {
        if (city.getCityId() == 0) throw new IllegalArgumentException("Can not update. City doesn't exist");
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.UPDATE_CITY)
        ){
            statement.setString(1, city.getCityName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getCityDistrict());
            statement.setInt(4, city.getCityPopulation());
            statement.setInt(5, city.getCityId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }
    @Override
    public int delete(City city) {
        int result = 0;
        try (
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(Querries.DELETE_CITY)
        ){
            statement.setInt(1, city.getCityId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
