package WeatherTest;

import Weather.WeatherForecast;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WeatherForecastTest {

    private final String API_KEY = "778909b9fe84fb35f150e83d127e3f49";
    private final String cityID = "588409";
    private WeatherForecast weatherForecast;

    @Before
    public void setUp() throws Exception {
        weatherForecast = new WeatherForecast();
    }

    public JsonObject getJsonObject() {
        HttpURLConnection httpURLConnection = weatherForecast.getHttpURLConnection("Tallinn");
        String jsonString = weatherForecast.readFromUrl(httpURLConnection);
        JsonObject jsonObject = weatherForecast.jsonParser(jsonString);
        return jsonObject;
    }

    @Test
    public void testHttpConnection() throws Exception{
        HttpURLConnection httpURLConnection = weatherForecast.getHttpURLConnection("Tallinn");
        assertEquals(httpURLConnection.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    public void testCheckJsonNotAbsent() {
        HttpURLConnection httpURLConnection = weatherForecast.getHttpURLConnection("Tallinn");
        String jsonString = weatherForecast.readFromUrl(httpURLConnection);
        assertTrue(jsonString.length() > 0);
    }

    @Test
    public void testJsonAfterParserNotEmpty() {
        JsonObject jsonObject = getJsonObject();
        assertNotEquals(null, jsonObject);
    }

    @Test
    public void testForecastForRightCityID() {
        JsonObject jsonObject = getJsonObject();
        assertEquals(588409, weatherForecast.getLocation(jsonObject).get("id").getAsInt());

    }

    @Test
    public void testForecastForRightCityName() {
        JsonObject jsonObject = getJsonObject();
        assertEquals("\"Tallinn\"", weatherForecast.getLocation(jsonObject).get("name").toString());
    }

    @Test
    public void testCheckRightLocation() {
        JsonObject jsonObject = getJsonObject();
        assertEquals("EE", weatherForecast.getLocation(jsonObject).get("country").getAsString());
    }


    @Test
    public void testRightCoordinates() {
        JsonObject jsonObject = getJsonObject();
        JsonObject coords = jsonObject.getAsJsonObject("coord");

        assertEquals(59.437, coords.get("lat").getAsDouble(), 0.01);
        assertEquals(24.7535, coords.get("lon").getAsDouble(), 0.01);
    }

    @Test
    public void testTemperatureNotBelow0() {
        JsonObject jsonObject = getJsonObject();
        Map<String, String> currentWeather = weatherForecast.getCurrentWeather(jsonObject);
        assertTrue(Double.valueOf(currentWeather.get("TEMPERATURE")) > 0);
    }
}