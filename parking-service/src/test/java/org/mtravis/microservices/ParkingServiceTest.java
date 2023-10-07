package org.mtravis.microservices;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.persistence.TicketRepository;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ParkingServiceTest {
    static TicketRepository mockerRepository = Mockito.mock(TicketRepository.class);

    @Inject
    TicketService service;

    @BeforeAll
    static void setup(){
        QuarkusMock.installMockForType(mockerRepository, TicketRepository.class);
    }

    @Test
    void test_generateTicket() {
        String licenseNumber = "1234";
        long parking_spot = 1234566;
        Assertions.assertEquals(service.generateTicket(licenseNumber, parking_spot).getParkingSpot(), parking_spot);
        Assertions.assertEquals(service.generateTicket(licenseNumber, parking_spot).getLicenseNumber(), licenseNumber);
    }

    @SneakyThrows
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_create(){
        Ticket ticket =
                Ticket.builder()
                        .id(UUID.randomUUID())
                        .entryTime(Instant.now())
                        .licenseNumber("1336")
                        .exitTime(Instant.now())
                        .build();
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(mockerRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        Assertions.assertTrue(service.create(ticket));
    }
}
