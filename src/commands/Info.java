package commands;

import exeptions.NoValidArgumentException;
import utility.CollectionManager;

public class Info implements Command<String>{
    private String name;
    private String description;
    private String usage;
    private CollectionManager collectionManager;

    public Info(String name, String description, String usage, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String[] argument) {
        String s = "type: " + collectionManager.getType() + "\n" +
               "initialization time: " + collectionManager.getInitializationTime().toString() + "\n" +
                "size: " + collectionManager.getCollection().size() + "\n";
        return s;
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
        if (arg[0].length() != 0) throw new NoValidArgumentException("illegal arg");
        return true;
    }
}
