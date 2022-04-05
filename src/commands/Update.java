package commands;

import data.City;
import data.Climate;
import data.StandardOfLiving;
import exсeptions.NoValidArgumentException;
import utility.CollectionManager;

public class Update implements Command<Boolean>{
    private String name;
    private String description;
    private String usage;
    private CollectionManager collectionManager;
    private int id;

    public Update(String name, String description, String usage, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.collectionManager = collectionManager;
    }

    @Override
    public Boolean execute(String[] argument) {
        City tempCity = collectionManager.getById(id);
        if (tempCity == null) return false;
        tempCity.setName(argument[0]);
        tempCity.getCoordinates().setX(Integer.valueOf(argument[1]));
        tempCity.getCoordinates().setY(Double.valueOf(argument[2]));
        tempCity.setArea(Integer.valueOf(argument[3]));
        tempCity.setPopulation(Integer.valueOf(argument[4]));
        if (argument[5].equals("")){
            tempCity.setMetersAboveSeaLevel(null);
        }
        else {
            tempCity.setMetersAboveSeaLevel(Double.valueOf(argument[5]));
        }
        if (argument[6].equals("")){
            tempCity.setTelephoneCode(null);
        }
        else {
            tempCity.setTelephoneCode(Integer.valueOf(argument[6]));
        }
        if (argument[7].equals("")){
            tempCity.setClimate(null);
        }
        else {
            tempCity.setClimate(Climate.valueOf(argument[7]));
        }
        if (argument[8].equals("")){
            tempCity.setStandardOfLiving(null);
        }
        else {
            tempCity.setStandardOfLiving(StandardOfLiving.valueOf(argument[8]));
        }
        if (argument[9].equals("")){
            tempCity.getGovernor().setName(null);
        }
        else {
            tempCity.getGovernor().setName(argument[9]);
        }
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

    @Override
    public boolean validateArgument(String[] arg) throws NoValidArgumentException {
        if (arg[0].length() == 0) throw new NoValidArgumentException("id not recognized");
        try {
            this.id = Integer.valueOf(arg[0]);
        } catch (NumberFormatException e) {
            throw new NoValidArgumentException("arg must be integer");
        }
        if (collectionManager.getById(this.id) == null){
            throw new NoValidArgumentException("no element with such id");
        }
        return true;
    }
}
