package org.mtravis.microservices;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SpotAllocationResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

    public static void main(String[] args) {
        System.out.println("INSERT INTO parking_spot(id, spot_number, spot_type, occupied) VALUES ('ef0779db-2ee6-4982-98c4-a1bb602b40ed', 1337, 'LARGE', false);");
        for (int i=0; i<20; i++){
            UUID id = UUID.randomUUID();
            long parkingSpot = new Random().nextLong();
            String[] spotType = new String[]{"SMALL","MEDIUM","LARGE", "XLARGE"};
            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
            // There is a bug here where the randomNum has a direct correlation to the occupied value
            // SMALL, LARGE values will be occupied/
            System.out.printf("INSERT INTO parking_spot(id, spot_number, spot_type, occupied) VALUES ('%s', %d, '%s', %b);\n"
                    , id, Math.abs(parkingSpot), spotType[randomNum], randomNum % 2 == 0);
        }
    }
}