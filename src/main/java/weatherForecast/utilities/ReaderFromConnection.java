package weatherForecast.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderFromConnection {

    private HttpConnection httpConnection = new HttpConnection();

    public ReaderFromConnection(HttpConnection connection) {
        httpConnection = connection;
    }

    public String readFromUrl(String city) throws IOException{

        StringBuilder content = new StringBuilder();

        BufferedReader bufferedReader = getReader(city);

        if (bufferedReader == null) {
            return null;
        }

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();

        httpConnection.closeConnection();
        return content.toString();
    }

    public BufferedReader getReader(String city) throws IOException {
        return new BufferedReader(
                new InputStreamReader(httpConnection.getHttpURLConnection(city)
                        .getInputStream()));
    }

    public void setHttpConnection(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    public HttpConnection getHttpConnection() {
        return this.httpConnection;
    }

}
