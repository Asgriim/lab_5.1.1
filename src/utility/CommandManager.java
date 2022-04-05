package utility;

import commands.Command;
import exсeptions.FileIssueException;
import exсeptions.NoValidArgumentException;
import exсeptions.ReaderInterruptionException;
import exсeptions.ScriptInputIssueException;
import readers.CityReader;
import readers.ScriptCityReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * operates commands and scripts
 */
public class CommandManager {
    private Map<String, Command> commandMap;
    private Command currentCommand;
    private String commandName;
    private String[] argument;
    private BufferedWriter writer;
    private CityReader cityReader;
    private Deque<String> scriptStack;
    private FileManager fileManager;
    private List<String> commandOutputStringNames;

    public CommandManager(FileManager fileManager,BufferedWriter writer,CityReader cityReader) {
        this.writer = writer;
        this.cityReader = cityReader;
        this.commandMap = new HashMap<>();
        this.argument = new String[1];
        this.scriptStack = new ArrayDeque<>();
        this.fileManager = fileManager;
        this.commandOutputStringNames = Arrays.asList("remove_by_id","clear","save","execute_script","exit","remove_first","remove_any_by_climate");
    }

    /**
     *
     * @param command user command in string representation
     * @throws IOException
     */
    public void handle(String command) throws IOException {
        parseInput(command);
        currentCommand = commandMap.get(commandName);
        if (currentCommand == null){
            writer.write("no such command\ntype help to see command list\n");
            writer.flush();
            return;
        }
        try {
            currentCommand.validateArgument(argument);
        } catch (NoValidArgumentException e) {
            writer.write(e.getMessage() + "\n");
            writer.write(currentCommand.getName() + ": " + currentCommand.getUsage() + "\n");
            writer.flush();
            return;
        }
        if (commandName.equals("add") || commandName.equals("update") || commandName.equals("add_if_max")){
            try {
                if ((boolean)currentCommand.execute(cityReader.read())){
                    writer.write(currentCommand.getName() + " executed successfully\n");
                    writer.flush();
                    return;
                }
                writer.write(currentCommand.getName() + " not executed\n");
                writer.flush();
                return;
            } catch (ReaderInterruptionException e) {
                writer.write(e.getMessage());
                writer.flush();
                return;
            }
        }
        if (commandOutputStringNames.contains(commandName)){
            if((boolean)currentCommand.execute(argument)){
                writer.write(currentCommand.getName() + " executed\n");
                writer.flush();
                return;
            }
            writer.write(currentCommand.getName() + " not executed\n");
            writer.flush();
            return;
        }
        writer.write((String) currentCommand.execute(argument));
        writer.flush();
    }

    /**
     * @param scriptReader - receives a script file reader
     * @throws ScriptInputIssueException
     * @throws IOException
     */
    private void scriptHandle(BufferedReader scriptReader) throws ScriptInputIssueException, IOException {
        parseInput(scriptReader.readLine());
        currentCommand = commandMap.get(commandName);
        if (currentCommand == null){
            throw new ScriptInputIssueException("script error\nno such command\n");
        }
        try {
            currentCommand.validateArgument(argument);
        } catch (NoValidArgumentException e) {
            throw new ScriptInputIssueException("script error\n"
                    + currentCommand.getName() + ": " + e.getMessage() + "\n");
        }
        if(commandName.equals("add") || commandName.equals("update") || commandName.equals("add_if_max")){
            try {
                currentCommand.execute(new ScriptCityReader(scriptReader).read());
                return;
            } catch (IOException e) {
                // TODO: 03.04.2022 чо то здесь придумать
                e.printStackTrace();
            } catch (ScriptInputIssueException e) {
                throw e;
            }
        }
        if(commandName.equals("execute_script")){
            if (scriptStack.contains(argument[0])){
                scriptStack.pop();
                throw new ScriptInputIssueException("script error\n script looping\n");
            }
            currentCommand.execute(argument);
            return;
        }
        if(!commandOutputStringNames.contains(commandName)){
            writer.write((String) currentCommand.execute(argument));
            writer.flush();
            return;
        }
        currentCommand.execute(argument);

    }

    /**
     * add command to a map
     * @param command
     */
    public void addCommand(Command command){
        this.commandMap.put(command.getName(),command);
    }

    /**
     *
     * @param scriptFile receives script file name
     * @throws IOException
     */
    public void execScript(String scriptFile) throws IOException {
        BufferedReader fileReader;
        try {
            fileReader = fileManager.getScriptFileReader(scriptFile);
            scriptStack.addFirst(scriptFile);
            while (fileReader.ready()){
                scriptHandle(fileReader);
            }
            scriptStack.pop();
            currentCommand = commandMap.get("execute_script");
        } catch (FileIssueException | ScriptInputIssueException | IOException e) {
            writer.write(e.getMessage());
            writer.flush();
            currentCommand = commandMap.get("execute_script");
        }
    }

    /**
     * parses user input to a command name and argument
     * @param s user input
     */
    private void parseInput(String s){
        String input = s.trim();
        String[] strings = input.split(" ",2);
        commandName = strings[0].toLowerCase();
        if (strings.length == 1){
            argument[0] = "";
            return;
        }
        argument[0] = strings[1];
    }

    /**
     *
     * @return map of commands
     */
    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
