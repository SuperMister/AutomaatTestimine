package weatherForecast.utilities;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.testng.Assert.*;

public class HttpConnectionTest {

    private HttpConnection httpConnection;

    @Before
    public void setUp() {
        httpConnection = new HttpConnection();
    }

    @Test
    public void testHttpConnectionWithRightArgument() throws Exception {
        assertEquals(httpConnection.getHttpURLConnection("Tallinn").getResponseCode(),
                HttpURLConnection.HTTP_OK);
    }

    @Test
    public void testHttpConnectionWithWrongArgument() throws Exception {
        assertEquals(httpConnection.getHttpURLConnection("Wrong City Name").getResponseCode(),
                HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Test
    public void testHttpConnectionWithWrongApiKey() throws Exception {
        httpConnection.setAPI_KEY("Wrong Api Key");
        assertEquals(httpConnection.getHttpURLConnection("Tallinn").getResponseCode(),
                HttpURLConnection.HTTP_UNAUTHORIZED);
    }

}