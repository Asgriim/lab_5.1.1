package commands;

import commands.Command;
import exeptions.NoValidArgumentException;

public class Exit implements Command<Boolean> {
    private String name;
    private String description;
    private String usage;

    public Exit(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    @Override
    public Boolean execute(String[] argument) {
        System.exit(0);
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
        if (arg[0].length() != 0) throw new NoValidArgumentException("illegal arg");
        return true;
    }
}
