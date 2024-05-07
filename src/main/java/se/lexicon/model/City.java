package se.lexicon.model;

import java.util.Objects;

public class City {
    //Fields
    private int cityId;
    private String cityName;
    private String countryCode;
    private String cityDistrict;
    private int cityPopulation;

    //Getters & Setters
    public int getCityId() {return cityId;}
    public String getCityName() {return cityName;}
    public String getCountryCode() {return countryCode;}
    public String getCityDistrict() {return cityDistrict;}
    public int getCityPopulation() {return cityPopulation;}

    //Constructor
    public City(int cityId, String cityName, String countryCode, String cityDistrict, int cityPopulation) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.cityDistrict = cityDistrict;
        this.cityPopulation = cityPopulation;
    }

    public City(String cityName,String countryCode,String cityDistrict,int cityPopulation) {
        this(0,cityName,countryCode,cityDistrict,cityPopulation);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityId == city.cityId && cityPopulation == city.cityPopulation &&
                Objects.equals(cityName, city.cityName) &&
                Objects.equals(countryCode, city.countryCode) &&
                Objects.equals(cityDistrict, city.cityDistrict);
    }
    @Override
    public int hashCode() {
        return Objects.hash(cityId, cityName, countryCode, cityDistrict, cityPopulation);
    }
    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", cityDistrict='" + cityDistrict + '\'' +
                ", cityPopulation=" + cityPopulation +
                '}';
    }
}
