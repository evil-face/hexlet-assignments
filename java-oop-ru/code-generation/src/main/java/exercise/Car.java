package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLOutput;

// BEGIN
@Value
// END
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
    public String serialize() {
        var mapper = new ObjectMapper();
        String serializedObject = null;
        try {
            serializedObject = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            System.out.println("Error while parsing Car object: ");
            e.printStackTrace();
        }
        return serializedObject;
    }

    public static Car unserialize(String jsonCar) {
        var mapper = new ObjectMapper();
        Car car = null;
        try {
            car = mapper.readValue(jsonCar, Car.class);
        } catch (IOException e) {
            System.out.println("Error while reading JSON: ");
            e.printStackTrace();
        }
        return car;
    }
    // END
}
