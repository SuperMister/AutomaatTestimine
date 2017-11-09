package weatherForecast.utilities;

import weatherForecast.utilities.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderFromConnection {


    private HttpConnection httpConnection = new HttpConnection();

    public ReaderFromConnection() {
        httpConnection = new HttpConnection();
    }

    public String readFromUrl(String city) {

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpConnection.getHttpURLConnection(city)
                            .getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(IOException e) {
            System.exit(1);
        }
        httpConnection.closeConnection();
        return content.toString();
    }

    public HttpConnection getHttpConnection() {
        return httpConnection;
    }

    public void setHttpConnection(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }


}
