import org.glassfish.grizzly.utils.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class GetInfFrOWM {    //Get info from OpenWeatherMap.org
    private static final String API = "ff8a0bead72382d0ab07d8ab63523a66";
    private static URLConnection urlConnection = null;
    private static URL urls = null;
    private static InputStreamReader isR = null;
    private static BufferedReader bfR = null;

    public static String checker(String city){
        String foundCity = GetInfFrOWM.checkCity(city);

        if(city.equals(foundCity)){
            return null;
        }else if(foundCity == null){
            return "Город не найден, проверьте введенные данные";
        }else{
            return String.format("Город с таким названием не найден, возможно вы имели ввиду '%s'", foundCity);
        }
    }

    private static String getHTML(String urlAdress){
        try {
            urls = new URL(urlAdress);
            urlConnection = urls.openConnection();
            isR = new InputStreamReader(urlConnection.getInputStream());
            bfR = new BufferedReader(isR);

            return bfR.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getJSONAr(String urlAdress){
        JSONArray allInfo = null;
        try {
            var htmlText = getHTML(urlAdress);
            var parser = new JSONParser();
            allInfo = (JSONArray) parser.parse(htmlText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(allInfo.size() == 0){
            return null;
        }else{
            return (JSONObject) allInfo.get(0);
        }
    }

    private static JSONObject getJSONOb(String urlAdress){
        JSONObject allInfo = null;
        try {
            var htmlText = getHTML(urlAdress);
            var parser = new JSONParser();
            allInfo = (JSONObject) parser.parse(htmlText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(allInfo.size() == 0){
            return null;
        }else{
            return allInfo;
        }
    }

    private static String checkCity(String nameCity){
        String getCity;
        final String urlAdress = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", nameCity, API);
        System.out.println(urlAdress);
        var InfoAboutCity = getJSONAr(urlAdress);

        if(InfoAboutCity == null){
            return null;
        }

        getCity = ((JSONObject) InfoAboutCity.get("local_names")).get("ru").toString();



        return getCity;
    }

    private static Pair<String, String> getCoordinates(String nameCity){
        String lat = null;
        String lon = null;
        final String urlAdress = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", nameCity, API);
        var InfoAboutCity = getJSONAr(urlAdress);

        lat = InfoAboutCity.get("lat").toString();
        lon = InfoAboutCity.get("lon").toString();

        return new Pair(lat, lon);
    }

    public static HashMap<String, String> getWeatherInCity(String city){
        var coord = getCoordinates(city);
        String lat = coord.getFirst();
        String lon = coord.getSecond();
        final String urlAdress = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&lang=ru&appid=%s&units=metric", lat, lon, API);
        var InfoAboutCity = getJSONOb(urlAdress);
        var outInfo = new HashMap<String, String>();

        outInfo.put("description", ((JSONObject)((JSONArray) InfoAboutCity.get("weather")).get(0)).get("description").toString());
        outInfo.put("temp", ((JSONObject)InfoAboutCity.get("main")).get("temp").toString());
        outInfo.put("feels_like", ((JSONObject)InfoAboutCity.get("main")).get("feels_like").toString());

        return outInfo;
    }
}
