package SR.Controller;

import SR.Model.Earthquake;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public List<Earthquake> makeRequest(String latitude, String longitude, int maxRadius) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> stringResponseEntity = rest.exchange(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&latitude="
                        + latitude + "&longitude=" + longitude + "&maxradius=" + maxRadius,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class
        );
        List<Earthquake> earthquakeList = new ArrayList<>();

        if (stringResponseEntity.getStatusCode() == HttpStatus.OK) {
            String json = stringResponseEntity.getBody();
            JsonObject newObject = new Gson().fromJson(json, JsonObject.class);
            JsonObject metadata = (JsonObject) newObject.get("metadata");
            int count = Integer.parseInt(String.valueOf(metadata.get("count")));

            System.out.println(count);
            if (count < 20) {
                return makeRequest(latitude, longitude, maxRadius + 5);

            }

            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("features");

            JSONObject jsonObject;
            JSONObject jsonGeometry;
            JSONObject jsonProperties;
            JSONArray jsonCoords;
            for (int i = 0; i < count; i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                jsonGeometry = jsonObject.getJSONObject("geometry");
                jsonProperties = jsonObject.getJSONObject("properties");
                jsonCoords = jsonGeometry.getJSONArray("coordinates");

                var mag = jsonProperties.get("mag");
                var lat = jsonCoords.get(1);
                var lon = jsonCoords.get(0);

                if (mag.getClass() == Integer.class){
                    mag = (double) (int) mag;
                }
                if (lat.getClass() == Integer.class){
                    lat = (double) (int) lat;
                }
                if (lon.getClass() == Integer.class){
                    lon = (double) (int) lon;
                }

                Earthquake earthquake = new Earthquake(
                        (double) mag,
                        (String) jsonProperties.get("title"),
                        (double) lat,
                        (double) lon,
                        calculateDistance( Double.valueOf(latitude),Double.valueOf(longitude),(double) lat,(double) lon)
                );
                addEarthquakeToList(earthquakeList, earthquake);
            }
        }
        return earthquakeList;
    }

    private void addEarthquakeToList(List<Earthquake> earthquakeList, Earthquake earthquake) {

    }

    private int calculateDistance(double lat1, double lat2, double lon1, double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return(int)(c * r);
    }
}

