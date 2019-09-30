package SR;
import SR.View.View;
import SR.Model.Earthquake;
import SR.Controller.Controller;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        View view = new View();
        List<String> inputValues;
        inputValues = view.getInputValues();
        Controller controller = new Controller();
        List<Earthquake> earthquakeList;
        earthquakeList = controller.makeRequest(inputValues.get(0), inputValues.get(1), 12);
        for (Earthquake a : earthquakeList){
            System.out.println(a.toString());
        }
    }
}
