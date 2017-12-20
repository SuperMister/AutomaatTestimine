package weatherForecast.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.testng.Assert.*;

public class ResponseJsonParserTest {

    private final String exampleJson = "{\"list\":[{\"main\":" +
            "{\"temp\":274.17,\"temp_min\":274.17,\"temp_max\":274.188},\"dt_txt\":\"2017-12-20 15:00:00\"}]," +
            " \"city\":{\"name\":\"Tallinn\",\"coord\": {\"lat\":59.4372, \"lon\":24.7454}}}";

    private ResponseJsonParser responseParser;

    @Before
    public void setUp() {
        responseParser = new ResponseJsonParser(exampleJson);
    }

    @Test
    public void testJsonObjectIsNotEmptyAfterParser() {
        assertNotEquals(null, responseParser.getApiResponseJsonObject());
    }

    @Test
    public void testGetCityNameAndFromExampleJson() {
        assertEquals("\"Tallinn\"", responseParser.getCity());
    }

    @Test
    public void testGetCoordinatesFromExampleJson() {
        assertEquals(59.4372, responseParser.getCityLatitude());
        assertEquals(24.7454, responseParser.getCityLongitude());
    }

    @Test
    public void testCoordinatesAreInRightRange() {
        double lat = responseParser.getCityLatitude();
        double lon = responseParser.getCityLongitude();
        assertTrue(-90 <= lat && lat <= 90);
        assertTrue(-180 <= lon && lon <= 180);
    }

    @Test
    public void testTemperatureIsRight() {
        assertEquals(274.17, responseParser.getCurrentTemperature());
    }

    @Test
    public void testJsonObjectNotEmptyAfterInitialization() {
        assertNotEquals(null, responseParser.getApiResponseJsonObject());
    }

    @Test
    public void testWeatherForecastMapIsNotNull() {
        assertNotEquals(null, responseParser.getForecastForThreeDays());
    }

}