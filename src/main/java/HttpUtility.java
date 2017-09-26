
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtility {
    public static HttpURLConnection getConnection(URL url) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }
}
