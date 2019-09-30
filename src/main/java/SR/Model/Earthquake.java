package SR.Model;

import java.util.Objects;

public class Earthquake implements Comparable{
    private double magnitudo;
    private String description;
    public double latitude;
    public double longitude;
    private int distance;

    @Override
    public String toString() {
        return "M: " + this.magnitudo + " - "  + this.description + " || " + this.distance +" km";
    }

    public Earthquake(double magnitudo, String description, double latitude, double longitude, int distance) {
        this.magnitudo = magnitudo;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public int compareTo(Object o) {

        Earthquake e = (Earthquake) o;
        if (this.distance > e.distance) return 1;
        if (this.distance == e.distance) return  0;
        else return -1;
    }
}
