package utility;

import data.City;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.PriorityQueue;

/**
 * helper class for parsing
 */
@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.FIELD)
 class CitiesQueue {
    @XmlElement(name = "City")
    private PriorityQueue<City> cities;
    public CitiesQueue(PriorityQueue<City> cities){
        this.cities = cities;
    }

    /**
     * empty constructor for parser
     */
    public CitiesQueue(){}
    public PriorityQueue<City> getCities() {
        return cities;
    }

    public void setCities(PriorityQueue<City> cities) {
        this.cities = cities;
    }
}
