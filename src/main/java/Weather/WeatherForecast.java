package Weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WeatherForecast {

    private static final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";

    private static final String cityId = "588409";


    public static void main(String[] args) throws Exception{
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=588409&APPID=" + API_KEY);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = readFromUrl(url);
        //System.out.println(jsonString);
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toString());
    }
    public static String readFromUrl(URL url) {
        String inputLine = null;
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputLine;
    }


}
