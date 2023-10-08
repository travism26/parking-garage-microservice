package org.mtravis.microservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.parsing.Parser;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.model.UserEntryDto;
import org.mtravis.microservices.model.Vehicle;
import org.mtravis.microservices.model.VehicleType;
import org.mtravis.microservices.resources.ParkingServicesApi;

import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserTicketResourceTest {

    Ticket ticket = Ticket.builder()
            .id(UUID.randomUUID())
            .licenseNumber("COOL")
            .entryTime(Instant.now())
            .build();
    UserEntryDto entryDto = UserEntryDto.builder()
            .vehicle(Vehicle.builder()
                    .licenseNumber("123")
                    .type(VehicleType.LARGE)
                    .build())
            .parkingSpot(1337)
            .id(UUID.randomUUID())
            .build();

    private final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @SneakyThrows
    @Test
    public void test_UserTicketResource() {
        given()
                .contentType("application/json")
                .body(JSON_MAPPER.writeValueAsString(entryDto))
                .when()
                .post("/api/ticket").then()
                .statusCode(202)
                .body("ticket_id", is("1aec4156-c19f-48ce-a303-c5c7ffb12572"));
    }

}