import org.glassfish.grizzly.utils.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetInfFrOWM {    //Get info from OpenWeatherMap.org
    private static String API = "ff8a0bead72382d0ab07d8ab63523a66";
    private static URLConnection urlConnection = null;
    private static URL urls = null;
    private static InputStreamReader isR = null;
    private static BufferedReader bfR = null;

    private static String GetHTML(String urlAdress){
        try {
            urls = new URL(urlAdress);
            urlConnection = urls.openConnection();
            isR = new InputStreamReader(urlConnection.getInputStream());
            bfR = new BufferedReader(isR);

            return bfR.readLine();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject GetJSON(String urlAdress){
        JSONArray allInfo = null;
        try {
            var htmlText = GetHTML(urlAdress);
            var parser = new JSONParser();
            allInfo = (JSONArray) parser.parse(htmlText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (JSONObject) allInfo.get(0);
    }

    public static Pair<String, String> getCoordinates(String nameCity){
        String lat = null;
        String lon = null;
        final String urlAdress = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", nameCity, API);
        var InfoAboutCity = GetJSON(urlAdress);

        lat = InfoAboutCity.get("lat").toString();
        lon = InfoAboutCity.get("lon").toString();

        return new Pair(lat, lon);
    }
}
