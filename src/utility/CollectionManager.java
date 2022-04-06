package utility;
import data.*;
import exсeptions.FileIssueException;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * operates a collection
 */
public class CollectionManager {
    private PriorityQueue<City> cityCollection;
    private LocalDateTime initializationTime;
    private String type = "PriorityQueue";
    private FileManager fileManager;

    /**
     *
     * @param fileManager
     */
    public CollectionManager(FileManager fileManager){
        this.fileManager = fileManager;
        this.cityCollection = new PriorityQueue<>();
        this.initializationTime = LocalDateTime.now();
    }

    /**
     *
     * @return city collection
     */
    public PriorityQueue<City> getCollection() {
        return cityCollection;
    }

    public City getById(int id){
        Iterator<City> iter = this.cityCollection.iterator();
        City tempCity;
        while (iter.hasNext()){
            tempCity = iter.next();
            if (tempCity.getId().equals(id) ){
                return tempCity;
            }
        }
        return null;
    }


    public Boolean removeById(int id){
        Iterator<City> iter = getIterator();
        while (iter.hasNext()){
            if (iter.next().getId().equals(id) ){
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public void addToCollect(City city){
        this.cityCollection.add(city);
    }

    public Boolean addIfMax(City city){
        if (this.cityCollection.size() > 0) {
            if (this.cityCollection.peek().compareTo(city) > 0 ) {
                addToCollect(city);
                return true;
            }
            else {return false;}
        }
        else {
            addToCollect(city);
            return true;
        }
    }

    public void clearCollection(){
        this.cityCollection.clear();
    }

    public int countBySOL(StandardOfLiving SOL){
        Iterator<City> iter = getIterator();
        int count = 0;
        while (iter.hasNext()){
            City tempCity = iter.next();
            if (tempCity.getClimate() == null) continue;
            if (tempCity.getStandardOfLiving().equals(SOL)){
                count++;
            }
        }
        return count;
    }

    public boolean removeAnyByClimate(Climate climate){
        Iterator<City> iter = getIterator();
        while (iter.hasNext()){
            City tempCity = iter.next();
            if (tempCity.getClimate() == null) continue;
            if (tempCity.getClimate().equals(climate)){
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public City removeHead(){
       return this.cityCollection.poll();
    }

    public boolean removeFirst(){
        if(this.cityCollection.poll() == null) return false;
        return true;
    }

    public Set<StandardOfLiving> getUniqueStandards(){
        Iterator<City> iter = getIterator();
        Set<StandardOfLiving> set = new HashSet<>();
        List<StandardOfLiving> list = new ArrayList<>();
        while (iter.hasNext()){
            City tempCity = iter.next();
            if (tempCity.getClimate() == null) continue;
            set.add(tempCity.getStandardOfLiving());
        }
        return set;
    }

    public String getStringCollection(){
        Iterator<City> iter = getIterator();
        String s = new String();
        while (iter.hasNext()){
            s = s + iter.next().toString() + "\n";
        }
        if (s.length() == 0) return "collection is empty";
        return s;
    }

    private Iterator<City> getIterator(){
        return  this.cityCollection.iterator();
    }

    private int createId(){
        if (!cityCollection.isEmpty()){
            Integer id = 0;
            Iterator<City> iter = getIterator();
            City tempCity;
            while (iter.hasNext()){
                tempCity = iter.next();
                if (tempCity.getId() > id) id = tempCity.getId();
            }
            return id + 1;
        }

        return 1;
    }

     public City createElement(String name,int x, int y, int area, int population, Double metersAboveSeaLevel,
                                int telephoneCode, Climate climate, StandardOfLiving standardLiving,String humanName ){
        return new City(createId(),name,new Coordinates(x,y),LocalDateTime.now(),area,population,metersAboveSeaLevel,
                telephoneCode,climate,standardLiving,new Human(humanName));
     }

     public Boolean saveCollectionToFile(){
        try {
            fileManager.parseToFile(cityCollection);
        } catch (FileIssueException | IOException | JAXBException e) {
            return false;
        }
        return true;
     }

    public LocalDateTime getInitializationTime() {
        return initializationTime;
    }

    public String getType() {
        return type;
    }

    public void setCityCollection(PriorityQueue<City> cityCollection) {
        this.cityCollection = cityCollection;
    }

    public boolean checkCollection(){
        boolean test = false;
        if(getCollection() == null || getCollection().isEmpty()){
            return test;
        }
        Iterator<City> iter = getIterator();
        City tempCity;
        Set<Integer> id = new HashSet<>();
        while (iter.hasNext()){
            tempCity = iter.next();
            id.add(tempCity.getId());
            if (tempCity.getId() <= 0 || tempCity.getId() == null ||
            tempCity.getName() == null || tempCity.getCoordinates() == null ||
            tempCity.getCoordinates().getX() == null ||  tempCity.getCoordinates().getX() <= -251 ||
            tempCity.getCreationDate() == null || tempCity.getArea() == null || tempCity.getPopulation() == null || tempCity.getTelephoneCode() == 0 ||
            tempCity.getTelephoneCode() < 0  || tempCity.getTelephoneCode() > 100000){
                return test;
            }
        }
        if (id.size() != getCollection().size()){
            return test;
        }
        test = true;
        return test;
    }
}
