package test;

import SR.Controller.Controller;
import SR.Model.Earthquake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewYork {

    private List<String> coordinates;

    @BeforeEach
    void setUp() {
        coordinates = new ArrayList<>();
        coordinates.add("40.730610");
        coordinates.add("-73.935242");
    }

    @Test
    void makeRequest() {
        Controller controller = new Controller();
        List<Earthquake> earthquakeList;
        earthquakeList = controller.makeRequest(coordinates.get(0), coordinates.get(1), 12);
        System.out.println("Test for New York");
        for (Earthquake a : earthquakeList){
            System.out.println(a.toString());
        }
        assertEquals(10, earthquakeList.size());
    }
}