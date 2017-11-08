package weatherForecast;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DataParser {



    public JsonObject jsonParser(String jsonString) {
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        return jsonElement.getAsJsonObject();
    }



}
