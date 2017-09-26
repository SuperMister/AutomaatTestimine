import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.fail;

public class HttpUtilityTest {

    private static final int HTTP_CODE_SUCCESS = 200;


/**
    @BeforeClass
    void setUpAllTests() {

    }

    @Before
    void setUpTest() {

    }*/



    @Test
    @DisplayName("😱")
    public void testHttpConnection() {
        try {
            String url = "";
            HttpURLConnection con = HttpUtility.getConnection(new URL("https://ained.ttu.ee"));
            assertEquals(con.getResponseCode(), HTTP_CODE_SUCCESS);
        } catch(IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}
