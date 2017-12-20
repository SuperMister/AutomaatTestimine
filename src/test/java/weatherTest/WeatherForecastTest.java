package weatherTest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weatherForecast.utilities.FileProcessor;
import weatherForecast.utilities.HttpConnection;
import weatherForecast.utilities.ReaderFromConnection;
import weatherForecast.utilities.ResponseJsonParser;
import weatherForecast.weather.WeatherForecast;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastTest {

    private final String exampleJson = "{\"list\":[{\"main\":" +
            "{\"temp\":274.17,\"temp_min\":274.17,\"temp_max\":274.188},\"dt_txt\":\"2017-12-20 15:00:00\"}]," +
            " \"city\":{\"name\":\"Tallinn\",\"coord\": {\"lat\":59.4372, \"lon\":24.7454}}}";

    private String exampleJsonString;
    private WeatherForecast weatherForecast;
    private ReaderFromConnection readerFromConnection;

    @Mock
    private FileProcessor fileProcessorMock;
    @Mock
    private ReaderFromConnection readerFromConnectionMock;

    @Before
    public void setUp() throws Exception {
        weatherForecast = new WeatherForecast();
        readerFromConnection = new ReaderFromConnection(new HttpConnection());
        exampleJsonString = readerFromConnection.readFromUrl("Sofia");

    }

    @Test
    public void testTemperatureNotBelow0() {
        ResponseJsonParser responseParser = new ResponseJsonParser(exampleJsonString);
        assertTrue(responseParser.getCurrentTemperature() > 0);
    }

    @Test
    public void testIfFileNotEmptyAfterGettingForecast() throws Exception {
        weatherForecast.getWeatherForecastForCitiesInFile();
        File file = new File("src\\main\\java\\weatherForecast\\output\\Sofia.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testFilesAreCreatedForAllCitiesInInputFile() throws Exception {
        weatherForecast.getFileProcessor().setInputFile("src\\test\\java\\weatherTest\\test_input.txt");
        weatherForecast.getFileProcessor().setPrefix("src\\test\\java\\weatherTest\\test_output\\");
        weatherForecast.getWeatherForecastForCitiesInFile();
        String[] neededCities = {"moscow", "helsinki", "stockholm"};
        for (int i = 0; i < neededCities.length; i++) {
            File file = new File("src\\test\\java\\weatherTest\\test_output\\" + neededCities[i] + ".txt");
            assertTrue(file.exists());
        }
    }

    @Test
    public void testWeatherForecastIsWrittenToFile() throws Exception {
        weatherForecast.setFileProcessor(fileProcessorMock);

        when(fileProcessorMock.readFromFile()).thenReturn("Tallinn");

        weatherForecast.getWeatherForecastForCitiesInFile();

        verify(fileProcessorMock, atLeast( 1)).writeToFile(anyString(), anyString());
    }

    @Test
    public void testWeatherForecastReadingFromFile() throws Exception {
        weatherForecast.setFileProcessor(fileProcessorMock);

        when(fileProcessorMock.readFromFile()).thenReturn("Moscow");

        weatherForecast.getWeatherForecastForCitiesInFile();

        verify(fileProcessorMock, atLeast(1)).readFromFile();
    }

    @Test
    public void testWeatherForecastOffline() throws Exception {
        weatherForecast.setReader(readerFromConnectionMock);
        when(readerFromConnectionMock.readFromUrl(anyString())).thenReturn(exampleJson);

        assertNotEquals(null, weatherForecast.getWeatherForecast("Tallinn"));
    }

    @Test
    public void testRightLocationForExampleOffline() throws Exception {
        weatherForecast.setReader(readerFromConnectionMock);
        when(readerFromConnectionMock.readFromUrl("Tallinn")).thenReturn(exampleJson);

        assertEquals("This city lat: 59.4372 ; lon: 24.7454", weatherForecast.getLocation("Tallinn"));
    }

    @Test
    public void testWeatherForecastFromConsoleIsNotNull() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Moscow".getBytes());
        System.setIn(inputStream);
        assertNotEquals(null, weatherForecast.getWeatherForecastFromConsole());
    }


    @Test
    public void testForecastCoversThreeDays() throws Exception {
        ResponseJsonParser responseParser = new ResponseJsonParser(exampleJsonString);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        TreeMap<String, JsonObject> forecast = responseParser.getForecastForThreeDays();

        String firstKey = forecast.firstKey();
        String lastKey = forecast.lastKey();

        LocalDateTime firstTimestamp = LocalDateTime.parse(firstKey, dateTimeFormatter);
        LocalDateTime lastTimestamp = LocalDateTime.parse(lastKey, dateTimeFormatter);

        assertEquals(lastTimestamp.getDayOfYear() - firstTimestamp.getDayOfYear(), 3);
    }
}