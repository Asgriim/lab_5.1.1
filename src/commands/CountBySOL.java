package commands;

import data.StandardOfLiving;
import exсeptions.NoValidArgumentException;
import utility.CollectionManager;

public class CountBySOL implements Command<String>{
    private String name;
    private String description;
    private String usage;
    private CollectionManager collectionManager;

    public CountBySOL(String name, String description, String usage, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String[] argument) {
        return String.valueOf(collectionManager.countBySOL(StandardOfLiving.valueOf(argument[0].toUpperCase()))) + "\n";
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
        if (arg[0].length() == 0) throw new NoValidArgumentException("illegal arg");
        String s = arg[0].toUpperCase();
        try {
            StandardOfLiving.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new NoValidArgumentException("no such standard of living");
        }
        return true;
    }
}
