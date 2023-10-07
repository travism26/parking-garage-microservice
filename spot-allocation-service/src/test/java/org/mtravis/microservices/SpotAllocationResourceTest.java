package org.mtravis.microservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mtravis.microservices.model.ParkingSpot;
import org.mtravis.microservices.model.ParkingSpotDto;
import org.mtravis.microservices.persistence.ParkingSpotType;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class SpotAllocationResourceTest {

    static SpotAllocationService mockedAllocationService = mock(SpotAllocationService.class);
    private final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    ParkingSpotDto spotDto = ParkingSpotDto.builder()
            .parkingSpot(123)
            .spotType(ParkingSpotType.XLARGE)
            .build();
    static ParkingSpot spot = ParkingSpot.builder()
            .spotNumber(123456)
            .occupied(false)
            .id(UUID.randomUUID())
            .spotType(ParkingSpotType.XLARGE)
            .build();

    @SneakyThrows
    @BeforeAll
    public static void setup(){
        QuarkusMock.installMockForType(mockedAllocationService, SpotAllocationService.class);
        when(mockedAllocationService.assignParkingSpot(any())).thenReturn(spot);
    }

    @SneakyThrows
    @Test
    public void testSpotAllocationEndpoint() {
        given()
        .contentType("application/json")
        .body(JSON_MAPPER.writeValueAsString(spotDto))
        .when().post("/api/spot/allocation")
        .then()
        .statusCode(202)
        .body("parking_spot", is(123456))
        .body("vehicle_size", is("XLARGE"));
    }

    public static void main(String[] args) {
        System.out.println("This main method is just used to generate INSERT statements for the database.");
        for (int i=0; i<20; i++){
            UUID id = UUID.randomUUID();
            long parkingSpot = new Random().nextLong();
            String[] spotType = new String[]{"SMALL","MEDIUM","LARGE", "XLARGE"};
            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
            // There is a bug here where the randomNum has a direct correlation to the occupied value
            // SMALL, LARGE values will be occupied.
            System.out.printf("INSERT INTO parking_spot(id, spot_number, spot_type, occupied) VALUES ('%s', %d, '%s', %b);\n"
                    , id, Math.abs(parkingSpot), spotType[randomNum], randomNum % 2 == 0);
        }
    }
}