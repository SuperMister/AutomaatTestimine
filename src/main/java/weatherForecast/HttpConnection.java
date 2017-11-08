package weatherForecast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    public static String API_KEY = "778909b9fe84fb35f150e83d127e3f49";


    public static HttpURLConnection getHttpURLConnection(String city) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=" + API_KEY);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
