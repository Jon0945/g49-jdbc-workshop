package se.lexicon;


import se.lexicon.data.CityDAOJDBC;
import se.lexicon.model.City;

public class App
{
    public static void main( String[] args ) {
        CityDAOJDBC cityDao = new CityDAOJDBC();
        System.out.println(cityDao.findById(2));
        System.out.println(cityDao.findByCode("KOR"));
        System.out.println(cityDao.findByName("Stockholm"));
        City Tjockhult = new City(3048,"Stockholm","SWE","Lisboa",750348);
        cityDao.update(Tjockhult);
    }
}
