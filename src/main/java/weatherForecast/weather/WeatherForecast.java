package weatherForecast.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import weatherForecast.utilities.FileProcessor;
import weatherForecast.utilities.HttpConnection;
import weatherForecast.utilities.ReaderFromConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class WeatherForecast {

    private ReaderFromConnection reader;

    private FileProcessor fileProcessor;

    public WeatherForecast() {
        reader = new ReaderFromConnection();
        fileProcessor = new FileProcessor();
    }

    public JsonObject jsonParser(String jsonString) {
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        return jsonElement.getAsJsonObject();
    }

    public JsonObject getLocation(String city) {
        String jsonString = reader.readFromUrl(city);
        return jsonParser(jsonString).getAsJsonObject("city");
    }

    public Map<String, String> getCurrentWeather(String city) {
        String jsonString = reader.readFromUrl(city);
        JsonObject jsonObject = jsonParser(jsonString);

        Map<String, String> map = new TreeMap<String, String>();
        JsonObject forecastLocation = jsonObject.getAsJsonObject("city");
        JsonArray jsonArray = jsonObject.getAsJsonArray("list");

        map.put("LOCATION", forecastLocation.toString());

        JsonObject object = jsonArray.get(0).getAsJsonObject();
        String date = object.get("dt_txt").getAsString();

        JsonObject currentWeather = object.get("main").getAsJsonObject();
        map.put("Date", date);
        map.put("TEMPERATURE", currentWeather.get("temp").toString());

        return map;
    }

    public Map<String, String> getWeatherForecast(String city) {
        String jsonString = reader.readFromUrl(city);
        JsonObject jsonObject = jsonParser(jsonString);

        Map<String, String> map = new TreeMap<String, String>();

        JsonObject forecastLocation = jsonObject.getAsJsonObject("city");
        JsonArray jsonArray = jsonObject.getAsJsonArray("list");

        map.put("LOCATION", forecastLocation.toString());

        Map<String, JsonElement> weatherInformation = new HashMap<String, JsonElement>();

        for (int i = 0; i < 25; i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();

            String date = object.get("dt_txt").getAsString();


            JsonObject weatherInfo = object.get("main").getAsJsonObject();

            weatherInformation.put("MIN_TEMPERATURE", weatherInfo.get("temp_min"));
            weatherInformation.put("MAX_TEMPERATURE", weatherInfo.get("temp_max"));

            map.put(date, weatherInformation.toString());

        }
        return map;
    }

    public Map<String, String> getForecastFromConsole () {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please, enter city: ");

        String city = scanner.next();

        System.out.println(getWeatherForecast(city));
        return getWeatherForecast(city);
    }

    public void forecastForCitiesInFile() {
        String[] cities = fileProcessor.readFromFile().split("\n");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < cities.length; i++) {
            output.append(getWeatherForecast(cities[i]));
            output.append("\n");
        }
        fileProcessor.writeToFile(output.toString());
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


    public static void main(String[] args) throws Exception{
        WeatherForecast weatherForecast = new WeatherForecast();
        System.out.println(weatherForecast.getCurrentWeather("Tallinn"));
    }

}
