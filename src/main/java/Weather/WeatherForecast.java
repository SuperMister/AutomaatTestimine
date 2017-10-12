package Weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WeatherForecast {

    private  final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";

    private HashMap<String, String> cityIDs; {
        cityIDs = new HashMap<String, String>();
        cityIDs.put("Tallinn", "588409");
    }

    public String readFromUrl(HttpURLConnection httpURLConnection) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public HttpURLConnection getHttpURLConnection(String city) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=" + cityIDs.get(city) + "&APPID=" + API_KEY);
                    connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public JsonObject jsonParser(String jsonString) {
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        return jsonElement.getAsJsonObject();
    }

    public JsonObject getLocation(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject("city");
    }

    public Map<String, String> getCurrentWeather(JsonObject jsonObject) {
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

    public Map<String, String> getWeatherForecast(JsonObject jsonObject) {

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
        HttpURLConnection httpURLConnection = wf.getHttpURLConnection("Tallinn");
        String jsonString = wf.readFromUrl(httpURLConnection);
        Map<String, String> a = wf.getCurrentWeather(wf.jsonParser(jsonString));
        System.out.println(a.toString());
    }

}
