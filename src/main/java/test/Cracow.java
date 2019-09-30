package test;

import SR.Controller.Controller;
import SR.Model.Earthquake;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Cracow {

    private List<String> coordinates;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        coordinates = new ArrayList<>();
        coordinates.add("50.049683");
        coordinates.add("19.944544");
    }

    @org.junit.jupiter.api.Test
    void makeRequest() {
        Controller controller = new Controller();
        List<Earthquake> earthquakeList;
        earthquakeList = controller.makeRequest(coordinates.get(0), coordinates.get(1), 12);
        System.out.println("Test for Cracow");
        for (Earthquake a : earthquakeList){
            System.out.println(a.toString());
        }
        assertEquals(10, earthquakeList.size());
    }
}