package WeatherTest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weatherForecast.utilities.FileProcessor;
import weatherForecast.utilities.HttpConnection;
import weatherForecast.utilities.ReaderFromConnection;
import weatherForecast.weather.WeatherForecast;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastTest {

    private final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";
    private final String cityID = "588409";
    private WeatherForecast weatherForecast;
    private HttpConnection httpConnection;
    private ReaderFromConnection readerFromConnection;

    @Mock
    private HttpConnection connectionMock;

    @Before
    public void setUp() throws Exception {
        weatherForecast = new WeatherForecast();
        httpConnection = new HttpConnection();
        readerFromConnection = new ReaderFromConnection();
    }

    @Test
    public void testHttpConnection() throws Exception{
        assertEquals(httpConnection.getHttpURLConnection("Tallinn").getResponseCode(),
                HttpURLConnection.HTTP_OK);
    }

    @Test
    public void testCheckJsonNotAbsent() {
        String jsonString = readerFromConnection.readFromUrl("London");
        assertNotEquals(null, jsonString);
    }

    @Test
    public void testJsonAfterParserNotEmpty() {
        assertNotEquals(null, weatherForecast.jsonParser("{\"id\":588409,\"name\":\"Tallinn\"," +
                "\"coord\":{\"lat\":59.437," +
                "\"lon\":24.7535},\"country\":\"EE\"}"));
    }

    @Test
    public void testForecastForRightCityID() {
        assertEquals(588409, weatherForecast.getLocation("Tallinn").get("id").getAsInt());

    }

    @Test
    public void testForecastForRightCityName() {
        assertEquals("\"London\"", weatherForecast.getLocation("London").get("name").toString());
    }

    @Test
    public void testCheckRightLocation() {
        assertEquals("EE", weatherForecast.getLocation("Tallinn").get("country").getAsString());
        assertEquals("BG", weatherForecast.getLocation("Sofia").get("country").getAsString());

    }

    @Test
    public void testRightCoordinates() {
        JsonObject location =  weatherForecast.getLocation("Tallinn");
        JsonObject coords = location.getAsJsonObject("coord");
        assertEquals(59.437, coords.get("lat").getAsDouble(), 0.01);
        assertEquals(24.7535, coords.get("lon").getAsDouble(), 0.01);
    }

    @Test
    public void testTemperatureNotBelow0() {
        //JsonObject jsonObject = getJsonObject();
        Map<String, String> currentWeather = weatherForecast.getCurrentWeather("Helsinki");
        assertTrue(Double.valueOf(currentWeather.get("TEMPERATURE")) > 0);
    }

    @Test (expected = IOException.class)
    public void testReaderThrowsExceptionWithWrongCity() {
        readerFromConnection.readFromUrl("noSuchCity");
    }

    @Test
    public void testInvalidKeyFails() throws Exception{
        httpConnection.setAPI_KEY("ksssassaksaks");
        assertEquals(401, httpConnection.getHttpURLConnection("Tallinn").getResponseCode());
    }

    @Test
    public void checkFileNotEmptyAfterGettingForecast() {
        weatherForecast.forecastForCitiesInFile();
        File file = new File("/Users/wiggily/AutomaatTestimine/src/main/java/weatherForecast/output/output.txt");
        assertTrue(file.length() > 0);
    }
    /**
    @Test
    public void testWeatherReadingFromConnection() throws Exception{
        ReaderFromConnection rd = new ReaderFromConnection();
        rd.setHttpConnection(connectionMock);
        rd.readFromUrl("Tallinn");

        verify(connectionMock, atLeast(1)).closeConnection();
    }*/

}