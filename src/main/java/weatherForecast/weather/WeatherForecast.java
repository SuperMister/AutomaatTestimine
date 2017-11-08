package weatherForecast.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import weatherForecast.HttpConnection;
import weatherForecast.ReaderFromConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WeatherForecast {

    private ReaderFromConnection reader = new ReaderFromConnection();

    private  final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";

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


    public static void main(String[] args) throws Exception{
        WeatherForecast wf = new WeatherForecast();
        System.out.println(wf.getWeatherForecast("tallinn"));
    }

}
