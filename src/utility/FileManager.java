package utility;


import data.City;
import exeptions.FileIssueException;

import java.io.*;
import javax.xml.bind.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;

/**
 * operates xml and scripts files
 */
public class FileManager {
    private String envVarName;
    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    /**
     *
     * @param envVar name of the environment variable
     * @throws JAXBException
     */
    public FileManager(String envVar) throws JAXBException {
        this.envVarName = envVar;
        this.context = JAXBContext.newInstance(CitiesQueue.class);
        this.marshaller = this.context.createMarshaller();
        this.unmarshaller = this.context.createUnmarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
    }

    /**
     * parse collection to file
     * @param cityPriorityQueue receives a queue to parse to a file
     * @return true if parsed successfully
     * @throws IOException
     * @throws JAXBException
     * @throws FileIssueException
     */
    public Boolean parseToFile(PriorityQueue<City> cityPriorityQueue) throws IOException, JAXBException, FileIssueException {
        checkFile(System.getenv().get(envVarName),true);
        FileWriter writer = new FileWriter(System.getenv().get(envVarName));
        BufferedWriter br = new BufferedWriter(writer);
        this.marshaller.marshal(getParseableCollection(cityPriorityQueue), br);
        return true;
    }

    /**
     * helper method for parsing
     * @param cityPriorityQueue
     * @return
     */
    private CitiesQueue getParseableCollection(PriorityQueue<City> cityPriorityQueue){
        return new CitiesQueue(cityPriorityQueue);
    }

    /**
     * parse collection from file
     * @return a city collection
     * @throws JAXBException
     * @throws FileIssueException
     * @throws FileNotFoundException
     */
    public PriorityQueue<City> parseFromFile() throws JAXBException, FileIssueException, FileNotFoundException {
        checkFile(System.getenv().get(envVarName),true);
        try {
            CitiesQueue citiesQueue = (CitiesQueue) this.unmarshaller.unmarshal(new FileReader(System.getenv().get(envVarName)));
            if (citiesQueue.getCities() == null){
                return new PriorityQueue<>();
            }
            return citiesQueue.getCities();
        } catch (NullPointerException e){
            return new PriorityQueue<>();
        }


    }

    /**
     * checks the file
     * @param path
     * @param writable
     * @return true if meets requirements
     * @throws FileIssueException
     */
    public Boolean checkFile(String path, boolean writable) throws FileIssueException {
        if (path == null){
            throw new FileIssueException("no such environment variable\nIt must be LABA=\"file_path\"");

        }
        Path file = Paths.get(path);
        if (!Files.exists(file)){
            throw new FileIssueException("file is not exists");
        }
        if (!Files.isReadable(file)){
            throw new FileIssueException("file is not readable");
        }
        if (!Files.isRegularFile(file)){
            throw new FileIssueException("file is not regular");
        }
        if (writable){
            if(!Files.isWritable(file)){
                throw new FileIssueException("file is not writable");
            }
        }
        return true;
    }

    /**
     *
     * @param fileName
     * @return file reader for script file
     * @throws FileIssueException
     */
    public BufferedReader getScriptFileReader(String fileName) throws FileIssueException {
        if (fileName == null){
            throw new FileIssueException("empty file name");
        }
        checkFile(fileName,false);
        Path file = Paths.get(fileName);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(file);
            return bufferedReader;
        } catch (IOException e) {
            throw new FileIssueException("something wrong with file");
        }
    }
}
