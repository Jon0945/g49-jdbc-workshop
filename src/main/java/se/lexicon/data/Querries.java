package se.lexicon.data;

public class Querries {
    public static final String FIND_BY_ID = "SELECT * FROM city WHERE ID = ?";
    public static final String FIND_BY_CODE = "SELECT * FROM city WHERE CountryCode = ?";
    public static final String FIND_BY_NAME = "SELECT * FROM city WHERE Name = ?";
    public static final String FIND_ALL = "SELECT * FROM city";
    public static final String ADD_CITY = "INSERT INTO city (Name,CountryCode,District,Population) VALUES(?,?,?,?)";
    public static final String UPDATE_CITY = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
    public static final String DELETE_CITY = "DELETE FROM city WHERE ID = ?";
}
