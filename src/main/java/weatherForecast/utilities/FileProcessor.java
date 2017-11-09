package weatherForecast.utilities;

import java.io.*;

public class FileProcessor {

    private String inputFile = "/Users/wiggily/AutomaatTestimine/src/main/java/weatherForecast/input/input.txt";

    private String outputFile = "/Users/wiggily/AutomaatTestimine/src/main/java/weatherForecast/output/output.txt";

    public String readFromFile() {
        String line = null;
        StringBuilder output = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(inputFile);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public void writeToFile(String text) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);

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

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

}
