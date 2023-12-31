package org.mtravis.microservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.parsing.Parser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mtravis.microservices.model.ParkingSpotType;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.model.Vehicle;

import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

@QuarkusTest
public class ParkingServiceResourceTest {

    private static final Vehicle vehicle = Vehicle.builder()
            .licenseNumber("123")
            .type(ParkingSpotType.MEDIUM)
            .build();
    private static final Ticket mockTicket = Ticket.builder()
            .entryTime(Instant.now())
            .id(UUID.randomUUID())
            .build();
    static TicketService mockedTicketService = mock(TicketService.class);
    private final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @SneakyThrows
    @BeforeAll
    public static void setup(){
        QuarkusMock.installMockForType(mockedTicketService, TicketService.class);
        Mockito.when(mockedTicketService.create(mockTicket)).thenReturn(true);
        Mockito.when(mockedTicketService.generateTicket(anyString(), anyLong())).thenReturn(mockTicket);
    }

    @SneakyThrows
    @Test
    public void testParkingServiceEndpoint() {
        given()
            .contentType("application/json")
            .body(JSON_MAPPER.writeValueAsString(vehicle))
            .when()
            .post("/api/parking/v2").then()
            .statusCode(202)
            .body("ticket_id", is(mockTicket.id.toString()))
            .defaultParser(Parser.JSON);
    }

}