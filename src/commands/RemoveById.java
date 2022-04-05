package commands;

import exсeptions.NoValidArgumentException;
import utility.CollectionManager;

public class RemoveById implements Command<Boolean>{
    private String name;
    private String description;
    private String usage;
    private CollectionManager collectionManager;

    public RemoveById(String name, String description, String usage, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.collectionManager = collectionManager;
    }

    @Override
    public Boolean execute(String[] argument) {
        return collectionManager.removeById(Integer.valueOf(argument[0]));
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
            Integer.valueOf(arg[0]);
        } catch (NumberFormatException e) {
            throw new NoValidArgumentException("arg must be integer");
        }
        return true;
    }
}
