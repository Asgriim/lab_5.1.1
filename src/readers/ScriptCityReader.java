package readers;

import data.City;
import data.Coordinates;
import data.Human;
import exсeptions.ScriptInputIssueException;
import exсeptions.WrongFieldFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * reads a fields from script
 */
public class ScriptCityReader {
    private ArrayList<String> fields;
    private BufferedReader reader;
    private String fileInput;

    public ScriptCityReader(BufferedReader reader){
        this.fields = new ArrayList<>(10);
        this.reader = reader;
        this.fileInput = new String();
    }

    /**
     *
     * @return array of fields in string representation
     * @throws IOException
     * @throws ScriptInputIssueException
     */
    public String[] read() throws IOException, ScriptInputIssueException {
        try {
            fileInput = reader.readLine();
            City.validateName(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            Coordinates.validateX(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            Coordinates.validateY(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            City.validateArea(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            City.validatePopulation(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            City.validateMetersAboveSea(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            City.validateTelephoneCode(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            fileInput = fileInput.toUpperCase();
            City.validateClimate(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            fileInput = fileInput.toUpperCase();
            City.validateSOL(fileInput);
            fields.add(fileInput);
            fileInput = reader.readLine();
            Human.validateName(fileInput);
            fields.add(fileInput);
            return fields.toArray(new String[0]);
        } catch (WrongFieldFormatException e) {
            throw new ScriptInputIssueException("Script error\nWrong collection element field format\n" + e.getMessage() + "\n");
        }

    }
}
