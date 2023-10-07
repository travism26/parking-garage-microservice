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
import org.mtravis.microservices.persistence.TicketEntity;
import org.mtravis.microservices.persistence.TicketRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ParkingServiceTest {
    static TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);

    @Inject
    TicketService service;

    @BeforeAll
    static void setup(){
        QuarkusMock.installMockForType(ticketRepository, TicketRepository.class);
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
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        Assertions.assertTrue(service.create(ticket));
    }

    @SneakyThrows
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_create_withConflict(){
        UUID id = UUID.randomUUID();
        Ticket ticket =
                Ticket.builder()
                        .id(id)
                        .build();
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.id = id;
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        when(panacheQuery.firstResult()).thenReturn(ticketEntity);

        Assertions.assertFalse(service.create(ticket));
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_delete(){
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);

        TicketEntity ticket = new TicketEntity();
        ticket.id = UUID.randomUUID();
        when(panacheQuery.firstResult()).thenReturn(ticket);

        Assertions.assertTrue(service.delete(ticket.id));
    }
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_delete_notExist(){
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        when(panacheQuery.firstResult()).thenReturn(null);

        Assertions.assertFalse(service.delete(UUID.randomUUID()));
    }

    @SneakyThrows
    @Test
    void test_getAll(){
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.id = UUID.randomUUID();

        when(ticketRepository.listAll()).thenReturn(List.of(ticketEntity));

        Assertions.assertEquals(1, service.getAll().size());
    }

    @SneakyThrows
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_getById(){
        UUID id = UUID.randomUUID();
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.id = id;

        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        when(panacheQuery.firstResult()).thenReturn(ticketEntity);

        Assertions.assertEquals(id, service.getById(id).id);
    }

    @SneakyThrows
    @Test
    void test_getById_NotFound(){
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(ticketRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        when(panacheQuery.firstResult()).thenReturn(null);
        Assertions.assertNull(service.getById(UUID.randomUUID()));
    }
}
