package commands;

import exсeptions.FileIssueException;
import exсeptions.NoValidArgumentException;
import utility.CommandManager;
import utility.FileManager;

import java.io.IOException;

public class ExecuteScript implements Command<Boolean>{
    private String name;
    private String description;
    private String usage;
    private CommandManager commandManager;
    private FileManager fileManager;

    public ExecuteScript(String name, String description, String usage, CommandManager commandManager, FileManager fileManager) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.commandManager = commandManager;
        this.fileManager = fileManager;
    }

    @Override
    public Boolean execute(String[] argument) {
        try {
            commandManager.execScript(argument[0]);
        } catch (IOException e) {
            e.printStackTrace();
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
        if (arg[0].length() == 0) throw new NoValidArgumentException("file not specified");
        try {
            fileManager.checkFile(arg[0],false);
        } catch (FileIssueException e) {
            throw new NoValidArgumentException(e.getMessage());
        }
        return true;
    }
}
