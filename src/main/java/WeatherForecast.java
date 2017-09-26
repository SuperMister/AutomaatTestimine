import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast {

    private static final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";


    public static void main(String[] args) throws Exception{
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=" + API_KEY);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
