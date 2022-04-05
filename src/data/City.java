package data;

import exсeptions.WrongFieldFormatException;
import xmlAdapter.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * main data class of 5th lab
 */
@XmlRootElement(name = "City")
@XmlAccessorType(XmlAccessType.FIELD)
public class City implements Comparable<City> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Double metersAboveSeaLevel;
    private Integer telephoneCode; //Значение поля должно быть больше 0, Максимальное значение поля: 100000
    private Climate climate; //Поле может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле может быть null

    public City(){}; // for jaxb
    public City(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Integer area,
                Integer population, Double metersAboveSeaLevel, Integer telephoneCode, Climate climate,
                StandardOfLiving standardOfLiving, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.telephoneCode = telephoneCode;
        this.climate = climate;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Double getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Integer getTelephoneCode() {
        return telephoneCode;
    }

    public Climate getClimate() {
        return climate;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Double metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setTelephoneCode(Integer telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", telephoneCode=" + telephoneCode +
                ", climate=" + climate +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(coordinates, city.coordinates) && Objects.equals(creationDate, city.creationDate) && Objects.equals(area, city.area) && Objects.equals(population, city.population) && Objects.equals(metersAboveSeaLevel, city.metersAboveSeaLevel) && Objects.equals(telephoneCode, city.telephoneCode) && climate == city.climate && standardOfLiving == city.standardOfLiving && Objects.equals(governor, city.governor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, area, population, metersAboveSeaLevel, telephoneCode, climate, standardOfLiving, governor);
    }

    public String getDateString(){
        return this.creationDate.toString();
    }

    @Override
    public int compareTo(City o) {
        return (o.getId() + o.getPopulation() + o.getArea()) - (this.id + this.population + this.area);
    }

    public static boolean validateName (String name) throws WrongFieldFormatException {
        if (name == null) throw new WrongFieldFormatException("can't be null");
        if (name.equals("")){
            throw new WrongFieldFormatException("name can't be empty");
        }
        else {
            return true;
        }
    }

    public static boolean validateArea(String area) throws WrongFieldFormatException {
        if (area == null) throw new WrongFieldFormatException("can't be null");
        Integer num;
        try {
            num = Integer.valueOf(area);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("area must be an integer");
        }
        if (num <= 0){
            throw new WrongFieldFormatException("area must greater 0");
        }
        return true;
    }

    public static boolean validatePopulation(String population) throws WrongFieldFormatException {
        if (population == null) throw new WrongFieldFormatException("can't be null");
        Integer num;
        try {
            num = Integer.valueOf(population);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("population must be an integer");
        }
        if (num <= 0){
            throw new WrongFieldFormatException("population must greater 0");
        }
        return true;
    }

    public static boolean validateMetersAboveSea(String metersAboveSea) throws WrongFieldFormatException {
        if (metersAboveSea == null) throw new WrongFieldFormatException("can't be null");
        Double num;
        try {
            Double.valueOf(metersAboveSea);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("meters above sea must be double");
        }
        return true;
    }

    public static boolean validateTelephoneCode(String telephoneCode) throws WrongFieldFormatException {
        if (telephoneCode == null) throw new WrongFieldFormatException("can't be null");
        Integer num;
        try {
            num = Integer.valueOf(telephoneCode);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("telephone code must be an integer");
        }
        if (num <= 0){
            throw new WrongFieldFormatException("telephone code must greater 0");
        }
        if (num >= 100000 ){
            throw new WrongFieldFormatException("telephone code must lower 100000");
        }
        return true;
    }

    public static boolean validateClimate(String climate) throws WrongFieldFormatException {
        if (climate == null) throw new WrongFieldFormatException("can't be null");
        try {
           Climate.valueOf(climate);
        } catch (IllegalArgumentException e) {
            throw new WrongFieldFormatException("no such climate");
        }
        return true;
    }

    public static boolean validateSOL(String sol) throws WrongFieldFormatException {
        if (sol == null) throw new WrongFieldFormatException("can't be null");
        try {
            StandardOfLiving.valueOf(sol);
        } catch (IllegalArgumentException e) {
            throw new WrongFieldFormatException("no such standard of living");
        }
        return true;
    }
}

