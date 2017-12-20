package weatherForecast.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    private String API_KEY = "778909b9fe84fb35f150e83d127e3f49";
    private HttpURLConnection connection;

    public HttpURLConnection getHttpURLConnection(String city) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=" + API_KEY);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (IOException e) {
            System.out.println("Something went wrong when connecting to API");
        }
        return connection;
    }

    public void closeConnection() {
        connection.disconnect();
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

}
