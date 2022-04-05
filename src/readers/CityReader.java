package readers;

import data.*;
import exсeptions.ReaderInterruptionException;
import exсeptions.WrongFieldFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
//мне стыдно за это

/**
 * read a city fields from console input
 */
public class CityReader {
    private ArrayList<String> fields;
    private BufferedReader reader;
    private OutputStreamWriter writer;
    private String userInput;
    public CityReader(BufferedReader reader, OutputStreamWriter writer){
        this.fields = new ArrayList<>(10);
        this.reader = reader;
        this.writer = writer;
        this.userInput = new String();
    }

    /**
     *
     * @return array of fields in string representation
     * @throws IOException
     * @throws ReaderInterruptionException
     */
    public String[] read() throws IOException, ReaderInterruptionException {
        fields.clear();
        boolean nameChecker = false;
        writer.write("\nEnter _q to leave this command\n");
        writer.flush();
        while (!nameChecker) {
            writer.write("type name: ");
            writer.flush();
            userInput = reader.readLine();
            checkLeave(userInput);
            try {
                nameChecker =  City.validateName(userInput);
            } catch (WrongFieldFormatException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        fields.add(userInput);
        writer.write("enter coordinates\n");
        writer.flush();
        boolean xChecker = false;
        while (!xChecker){
            writer.write("type x: ");
            writer.flush();
            userInput = reader.readLine();
            checkLeave(userInput);
            try {
                xChecker = Coordinates.validateX(userInput);
            } catch (WrongFieldFormatException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        fields.add(userInput);
        boolean yChecker = false;
        while (!yChecker){
            writer.write("type y: ");
            writer.flush();
            userInput = reader.readLine();
            checkLeave(userInput);
            try {
                yChecker = Coordinates.validateY(userInput);
            } catch (WrongFieldFormatException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        fields.add(userInput);
        boolean areaChecker = false;
        while (!areaChecker){
            writer.write("type area: ");
            writer.flush();
            userInput = reader.readLine();
            checkLeave(userInput);
            try {
                areaChecker = City.validateArea(userInput);
            } catch (WrongFieldFormatException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        fields.add(userInput);
        boolean populationChecker = false;
        while (!populationChecker){
            writer.write("type population: ");
            writer.flush();
            userInput = reader.readLine();
            checkLeave(userInput);
            try {
                populationChecker = City.validatePopulation(userInput);
            } catch (WrongFieldFormatException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        fields.add(userInput);
        boolean decision = getDecision("Do you want to enter meters above sea level?");
        if (!decision){
            fields.add("");
        }
        else {
            boolean metersAboveSeaChecker = false;
            while (!metersAboveSeaChecker){
                writer.write("type meters above sea: ");
                writer.flush();
                userInput = reader.readLine();
                checkLeave(userInput);
                try {
                   metersAboveSeaChecker = City.validateMetersAboveSea(userInput);
                } catch (WrongFieldFormatException e) {
                    writer.write(e.getMessage() + "\n");
                    writer.flush();
                }
            }
            fields.add(userInput);
        }
        decision = getDecision("Do you want to enter telephone code?");
        if (!decision){
            fields.add("");
        }
        else {
            boolean telephoneCodeChecker = false;
            while (!telephoneCodeChecker){
                writer.write("type telephone code: ");
                writer.flush();
                userInput = reader.readLine();
                checkLeave(userInput);
                try {
                    telephoneCodeChecker = City.validateTelephoneCode(userInput);

                } catch (WrongFieldFormatException e) {
                    writer.write(e.getMessage() + "\n");
                    writer.flush();
                }
            }
            fields.add(userInput);
        }

        decision = getDecision("Do you want to enter climate?");
        if (!decision){
            fields.add("");
        }
        else {
            boolean climateChecker = false;
            writer.write("Enter climate const from this list: " + Arrays.asList(Climate.values()).toString() + "\n");
            writer.flush();
            while (!climateChecker){
                writer.write("type climate: ");
                writer.flush();
                userInput = reader.readLine();
                checkLeave(userInput);
                userInput = userInput.toUpperCase();
                try {
                    climateChecker = City.validateClimate(userInput);
                } catch (WrongFieldFormatException e) {
                    writer.write(e.getMessage() + "\n");
                    writer.flush();
                }
            }
            fields.add(userInput);
        }



        decision = getDecision("Do you want to enter standard of living?");
        if (!decision){
            fields.add("");
        }
        else {
            boolean solChecker = false;
            writer.write("Enter standard of living const from this list: " + Arrays.asList(StandardOfLiving.values()).toString() + "\n");
            writer.flush();
            while (!solChecker){
                writer.write("type standard of living: ");
                writer.flush();
                userInput = reader.readLine();
                checkLeave(userInput);
                userInput = userInput.toUpperCase();
                try {
                    solChecker = City.validateSOL(userInput);
                } catch (WrongFieldFormatException e) {
                    writer.write(e.getMessage() + "\n");
                    writer.flush();
                }
            }
            fields.add(userInput);
        }

        decision = getDecision("Do you want to enter governor?");
        if (!decision){
            fields.add("");
        }
        else {
            nameChecker = false;
            while (!nameChecker) {
                writer.write("type name: ");
                writer.flush();
                userInput = reader.readLine();
                checkLeave(userInput);
                try {
                    nameChecker = Human.validateName(userInput);
                } catch (WrongFieldFormatException e) {
                    writer.write(e.getMessage() + "\n");
                    writer.flush();
                }
            }
            fields.add(userInput);
        }
        return fields.toArray(new String[0]);
    }

    private void checkLeave(String userInput) throws ReaderInterruptionException {
        if (userInput.equals("_q")){
            throw new ReaderInterruptionException("command was interrupted by user\n");
        }
    }

    private boolean getDecision(String question) throws IOException, ReaderInterruptionException {
        writer.write(question + "\n");
        writer.flush();
        while (true){
            writer.write("type Y/n: ");
            writer.flush();
            userInput = reader.readLine();
            userInput = userInput.toLowerCase();
            checkLeave(userInput);
            if (userInput.equals("y")) {
                return true;
            }
            if (userInput.equals("n")) {
                return false;
            }
        }
    }
}
