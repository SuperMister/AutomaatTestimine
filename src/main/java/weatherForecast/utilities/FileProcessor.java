package weatherForecast.utilities;

import java.io.*;

public class FileProcessor {

    private String inputFile = "src\\main\\java\\weatherForecast\\input\\input.txt";

    private String prefix = "src\\main\\java\\weatherForecast\\output\\";

    public String readFromFile() {
        String line;
        StringBuilder output = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(inputFile);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {

                output.append(line).append("\n");
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public void writeToFile(String text, String city) {
        try {
            FileWriter fileWriter = new FileWriter(prefix + "\\" + city + ".txt");

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(text);

            bufferedWriter.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
