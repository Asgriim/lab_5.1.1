package utility;

import commands.*;
import data.Climate;
import data.StandardOfLiving;
import exсeptions.FileIssueException;
import readers.CityReader;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Arrays;

/**
 * Create all instances and read user input
 */
public class UserSession {
    /**
     *
     * @param envVar name of the environment variable
     * @throws JAXBException parser exception
     */
    public void run(String envVar) throws JAXBException {
        FileManager fileManager = new FileManager(envVar);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        try {
            collectionManager.setCityCollection(fileManager.parseFromFile());
        } catch (FileIssueException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (JAXBException e){
            System.out.println("Xml file error\n" +
                    "wrong xml data");
            System.exit(1);
        }
        if (!collectionManager.checkCollection()) {
            System.out.println("wrong xml data\nenter fields in file properly");
            System.exit(1);
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
        CommandManager commandManager = new CommandManager(fileManager,new BufferedWriter(outputStreamWriter),
                new CityReader(new BufferedReader(new InputStreamReader(System.in)),outputStreamWriter));
        commandManager.addCommand(new Add("add","insert new element in collection","doesn't receive an argument",collectionManager));
        commandManager.addCommand(new AddIfMax("add_if_max","insert new element in collection if it's greater than max","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new Clear("clear","clears collection","doesn't receive an argument",collectionManager));
        commandManager.addCommand(new CountBySOL("count_by_standard_of_living","print the number of elements is equal to the specified value",
                "receive standard of living const as argument: " + Arrays.toString(StandardOfLiving.values()),collectionManager));
        commandManager.addCommand(new ExecuteScript("execute_script","execute script from a file","receives a file name as argument,\n\tfile mast be readable and in current directory",
                commandManager,fileManager));
        commandManager.addCommand(new Exit("exit","exit programm without saving","doesn't receive an argument"));
        commandManager.addCommand(new Help("help","print a list of commands or description of specified one","can receive an argument as command name\n\t for example: help info",
                commandManager));
        commandManager.addCommand(new Info("info","print information about collection","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new PrintUniqueSOL("print_unique_standard_of_living","print unique standard of living values","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new RemoveAnyByClimate("remove_any_by_climate climate","remove firs element that equals specified",
                "receive climate const as argument: " + Arrays.toString(Climate.values()),collectionManager));
        commandManager.addCommand(new RemoveById("remove_by_id","remove element by id","receives integer value as argument",
                collectionManager));
        commandManager.addCommand(new RemoveFirst("remove_first","remove first element","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new RemoveHead("remove_head","print and delete first element","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new Save("save","save collection to a file","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new Show("show","print all elements of collection","doesn't receive an argument",
                collectionManager));
        commandManager.addCommand(new Update("update","update value of element whose id is equal to specified","receive an integer value as argument",
                collectionManager));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true){
                System.out.print("enter command: ");
                commandManager.handle(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
