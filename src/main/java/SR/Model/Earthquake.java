package SR.Model;

public class Earthquake {
    double magnitudo;
    String description;
    double latitude;
    double longitude;
    int distance;

    @Override
    public String toString() {
        return "M: " + this.magnitudo + " - " + this.description + " || " + this.distance;
    }

    public Earthquake(double magnitudo, String description, double latitude, double longitude, int distance) {
        this.magnitudo = magnitudo;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
