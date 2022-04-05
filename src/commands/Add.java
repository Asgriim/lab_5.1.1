package commands;

import data.City;
import data.Climate;
import data.StandardOfLiving;
import exeptions.NoValidArgumentException;
import utility.CollectionManager;

public class Add implements Command<Boolean>{
    private String name;
    private String description;
    private String usage;
    private CollectionManager collectionManager;
    public Add(String name, String description, String usage, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.collectionManager = collectionManager;
    }

    @Override
    public Boolean execute(String[] argument) {
        collectionManager.addToCollect(createElement(argument));
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    @Override
    public boolean validateArgument(String[] arg) throws NoValidArgumentException {
        if (arg[0].length() != 0) throw new NoValidArgumentException("illegal arg");
        return true;
    }

    City createElement(String[] fields){
        City tempCity = collectionManager.createElement(null,0,0,0,0,null,0,
                null,null,null);
        tempCity.setName(fields[0]);
        tempCity.getCoordinates().setX(Integer.valueOf(fields[1]));
        tempCity.getCoordinates().setY(Double.valueOf(fields[2]));
        tempCity.setArea(Integer.valueOf(fields[3]));
        tempCity.setPopulation(Integer.valueOf(fields[4]));
        if (fields[5].equals("")){
            tempCity.setMetersAboveSeaLevel(null);
        }
        else {
            tempCity.setMetersAboveSeaLevel(Double.valueOf(fields[5]));
        }
        if (fields[6].equals("")){
            tempCity.setTelephoneCode(null);
        }
        else {
            tempCity.setTelephoneCode(Integer.valueOf(fields[6]));
        }
        if (fields[7].equals("")){
            tempCity.setClimate(null);
        }
        else {
            tempCity.setClimate(Climate.valueOf(fields[7]));
        }
        if (fields[8].equals("")){
            tempCity.setStandardOfLiving(null);
        }
        else {
            tempCity.setStandardOfLiving(StandardOfLiving.valueOf(fields[8]));
        }
        if (fields[9].equals("")){
            tempCity.getGovernor().setName(null);
        }
        else {
            tempCity.getGovernor().setName(fields[9]);
        }
        return tempCity;
    }
}
