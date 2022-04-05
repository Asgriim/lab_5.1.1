package commands;

import exeptions.NoValidArgumentException;
import utility.CommandManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Help implements Command<String> {
    private String name;
    private String description;
    private String usage;
    private CommandManager commandManager;

    public Help(String name, String description, String usage, CommandManager commandManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.commandManager = commandManager;
    }

    @Override
    public String execute(String[] argument) {
        String s = new String();
        if(argument[0].length() == 0){
            s = commandManager.getCommandMap().values().stream().map(x -> "\t" + x.getName() + ": " + x.getDescription()).collect(Collectors.joining("\n"));
            return s + "\n";
        }
        Command tempComm = commandManager.getCommandMap().get(argument[0]);
        s = "\t" + tempComm.getName() + ": " + tempComm.getDescription() + "\n\t" + tempComm.getUsage() + "\n";
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
        if(arg[0].length() != 0){
            if (commandManager.getCommandMap().get(arg[0]) == null){
                throw new NoValidArgumentException("illegal arg");
            }
        }
        return true;
    }
}
