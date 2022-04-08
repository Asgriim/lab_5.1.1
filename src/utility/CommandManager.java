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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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

    public CommandManager(FileManager fileManager,BufferedWriter writer,CityReader cityReader) {
        this.writer = writer;
        this.cityReader = cityReader;
        this.commandMap = new HashMap<>();
        this.argument = new String[1];
        this.scriptStack = new ArrayDeque<>();
        this.fileManager = fileManager;
    }

    /**
     *
     * @param command user command in string representation
     * @throws IOException
     */
    public void handle(String command) throws IOException {
        parseInput(command);
        if (!commandMap.containsKey(commandName)){
            writer.write("no such command\ntype help to see command list\n");
            writer.flush();
            return;
        }
        currentCommand = commandMap.get(commandName);
        try {
            currentCommand.validateArgument(argument);
        } catch (NoValidArgumentException e) {
            writer.write(e.getMessage() + "\n");
            writer.write(currentCommand.getName() + ": " + currentCommand.getUsage() + "\n");
            writer.flush();
            return;
        }
        if (currentCommand.getClass().getSimpleName().equals("Add") || currentCommand.getClass().getSimpleName().equals("Update")
                || currentCommand.getClass().getSimpleName().equals("AddIfMax")){
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
        try {
            if (currentCommand.getClass().getDeclaredMethod("execute", String[].class).getReturnType().getSimpleName().equals("Boolean")){
                if((boolean)currentCommand.execute(argument)){
                    writer.write(currentCommand.getName() + " executed\n");
                    writer.flush();
                    return;
                }
                writer.write(currentCommand.getName() + " not executed\n");
                writer.flush();
                return;
            }
        } catch (NoSuchMethodException e) {
            writer.write("unexpected error");
            writer.flush();
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
        if (!commandMap.containsKey(commandName)){
            throw new ScriptInputIssueException("script error\nno such command\n");
        }
        currentCommand = commandMap.get(commandName);
        try {
            currentCommand.validateArgument(argument);
        } catch (NoValidArgumentException e) {
            throw new ScriptInputIssueException("script error\n"
                    + currentCommand.getName() + ": " + e.getMessage() + "\n");
        }
        if(currentCommand.getClass().getSimpleName().equals("Add") || currentCommand.getClass().getSimpleName().equals("Update") ||
                currentCommand.getClass().getSimpleName().equals("AddIfMax")){
            try {
                currentCommand.execute(new ScriptCityReader(scriptReader).read());
                return;
            } catch (IOException e) {
                // TODO: 03.04.2022 чо то здесь придумать
                e.printStackTrace();
            } catch (ScriptInputIssueException e) {
                throw new ScriptInputIssueException("error in "+ currentCommand.getName()+ " command" + "\n" +  e.getMessage());
            }
        }
        if(currentCommand.getClass().getSimpleName().equals("ExecuteScript")){
            if (scriptStack.contains(argument[0])){
                scriptStack.pop();
                throw new ScriptInputIssueException("script error\n script looping\n");
            }
            currentCommand.execute(argument);
            return;
        }
        try {
            if(currentCommand.getClass().getDeclaredMethod("execute", String[].class).getReturnType().getSimpleName().equals("String")){
                writer.write((String) currentCommand.execute(argument));
                writer.flush();
                return;
            }
        } catch (NoSuchMethodException e) {
            writer.write("unexpected script error");
            writer.flush();
        }
        currentCommand.execute(argument);

    }

    /**
     * add command to a map
     * @param command command object to add
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
