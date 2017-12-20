package weatherForecast.weather;

import com.google.gson.*;
import weatherForecast.utilities.FileProcessor;
import weatherForecast.utilities.HttpConnection;
import weatherForecast.utilities.ResponseJsonParser;
import weatherForecast.utilities.ReaderFromConnection;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class WeatherForecast {

    private ReaderFromConnection reader;

    private FileProcessor fileProcessor;

    public WeatherForecast() {
        reader = new ReaderFromConnection(new HttpConnection());
        fileProcessor = new FileProcessor();
    }

    public String getLocation(String city) throws Exception{
        String jsonString = reader.readFromUrl(city);
        ResponseJsonParser responseJsonParser = new ResponseJsonParser(jsonString);
        return String.format("This city lat: %s ; lon: %s",
                responseJsonParser.getCityLatitude(), responseJsonParser.getCityLongitude());
    }

    public String getCurrentWeather(String city) throws Exception{
        String jsonString = reader.readFromUrl(city);
        ResponseJsonParser responseJsonParser = new ResponseJsonParser(jsonString);

        return String.format("Current weather in %s : %s",
                responseJsonParser.getCity(), responseJsonParser.getCurrentTemperature());
    }

    public String getWeatherForecast(String city) throws Exception {
        return generateOutputStringForWeatherForecast(city);
    }

    public String getWeatherForecastFromConsole () throws Exception{

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please, enter city: ");

        String city = scanner.next();

        System.out.println(generateOutputStringForWeatherForecast(city));

        return getWeatherForecast(city);
    }

    public void getWeatherForecastForCitiesInFile() throws Exception{
        String[] cities = fileProcessor.readFromFile().split("\n");

        for (String city : cities) {
            String output = generateOutputStringForWeatherForecast(city);

            fileProcessor.writeToFile(output, city.trim());
        }
    }

    public String generateOutputStringForWeatherForecast(String city) throws Exception{
        String jsonString = reader.readFromUrl(city);
        ResponseJsonParser responseJsonParser = new ResponseJsonParser(jsonString);
        StringBuilder output = new StringBuilder();

        String forecastForCurrentCity = getCurrentWeather(city);

        output.append(forecastForCurrentCity).append("\n\n");
        output.append(getLocation(city)).append("\n\n");
        output.append(String.join("", Collections.nCopies(50, "="))).append("\n\n");

        for (Map.Entry<String, JsonObject> entry : responseJsonParser.getForecastForThreeDays().entrySet()) {

            output.append(entry).append("\n\n");

        }
        return output.toString();
    }

    public ReaderFromConnection getReader() {
        return reader;
    }

    public void setReader(ReaderFromConnection reader) {
        this.reader = reader;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
}
