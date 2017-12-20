package weatherForecast.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ReaderFromConnectionTest {

    private HttpConnection httpConnection;

    private ReaderFromConnection reader;

    @Mock
    private HttpConnection httpConnectionMock;

    @Before
    public void setUp() {
        httpConnection = new HttpConnection();
        reader = new ReaderFromConnection(httpConnection);
    }

    @Test
    public void testNotNullAfterReadFromConnection() throws Exception {
        String informationFromConnection = reader.readFromUrl("Sofia");
        assertNotEquals(null, informationFromConnection);
    }

    @Test (expected = IOException.class)
    public void testReturnNullIfNoSuchCity() throws Exception {
        BufferedReader bufferedReader = reader.getReader("Wrong City");
        assertEquals(null, bufferedReader);
    }

    @Test
    public void testReaderClosesHttpConnection() throws Exception{
        reader.setHttpConnection(httpConnectionMock);

        when(httpConnectionMock.getHttpURLConnection("Tallinn"))
                .thenReturn((HttpURLConnection) new URL("http://google.com").openConnection());

        reader.readFromUrl("Tallinn");
        verify(httpConnectionMock, atLeast(1)).closeConnection();

    }

    @Test (expected = IOException.class)
    public void testExceptionIfNoSuchCity() throws Exception {
        reader.readFromUrl("No Such City");
    }
}