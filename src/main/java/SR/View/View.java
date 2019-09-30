package SR.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    public List<String> getInputValues() {
        System.out.println("Example of correct data: \n\tType latitude coord:	30.15\n\t\t" +
                "Type longitude coord:	25\n\t\t\t" +
                "latitude range -90 to 90\n\t\t\t\tlongitude range -180 to 180");
        System.out.println("Type input latitude coordinate: ");
        Scanner scanner = new Scanner(System.in);
        String latitude = scanner.nextLine();
        System.out.println("Type input longitude coordinate: ");
        String longitude = scanner.nextLine();

        try{
            double latitudeAsFloat = Double.parseDouble(latitude);
            double longitudeAsFloat = Double.parseDouble(longitude);

            if (!checkParameters(latitudeAsFloat, longitudeAsFloat)){
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            return getInputValues();
        }
        List<String> returnValues = new ArrayList<>();
        returnValues.add(latitude);
        returnValues.add(longitude);
        return returnValues;
    }
    private boolean checkParameters(double latitudeAsFloat, double longitudeAsFloat){
        return !(latitudeAsFloat > 180.0) && !(latitudeAsFloat < -180.0) &&
                !(longitudeAsFloat > 90.0) && !(longitudeAsFloat < -90.0);
    }
}
