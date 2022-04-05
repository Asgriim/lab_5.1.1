import exeptions.ReaderInterruptionException;
import utility.UserSession;

import javax.xml.bind.JAXBException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ReaderInterruptionException, JAXBException {
//        FileManager fileManager = new FileManager("LABA");
//        CollectionManager collectionManager = new CollectionManager(fileManager);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        CityReader cityReader = new CityReader(reader, new OutputStreamWriter(System.out));
//        collectionManager.addToCollect(collectionManager.createElement("1",1,2,3,4,6.2,123,
//                Climate.MEDITERRANIAN,StandardOfLiving.HIGH,"123"));
//        PriorityQueue<City> q = new PriorityQueue<>();
//
//        CommandManager commandManager = new CommandManager(fileManager,new BufferedWriter(new OutputStreamWriter(System.out)),cityReader);
//        Command add = new Add("add","add element","no args",collectionManager);
//        commandManager.addCommand(add);
//        commandManager.addCommand(new ExecuteScript("execute_script","exec script","txt arg",commandManager,fileManager));
//        commandManager.addCommand(new Info("info","inf ","aboba",collectionManager));
//        commandManager.addCommand(new Help("help","desc","usage",commandManager));
//        commandManager.addCommand(new Show("show","string collection","no args",collectionManager));
//        commandManager.addCommand(new Update("update","update elem","int arg",collectionManager));
//        commandManager.addCommand(new RemoveById("remove_by_id","remove e","id arg",collectionManager));
//        commandManager.addCommand(new RemoveAnyByClimate("remove_any_by_climate","remove climate","arg climate",collectionManager));
//        commandManager.addCommand(new CountBySOL("count_by_standard_of_living","count sol","sol arg",collectionManager));
//        System.out.println(collectionManager.getCollection());
//        commandManager.handle(reader.readLine());
//        commandManager.handle(reader.readLine());
        UserSession userSesion = new UserSession();
        userSesion.run("LABA");


    }
}
