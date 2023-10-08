package org.mtravis.microservices.resources;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mtravis.microservices.model.Vehicle;

import java.time.Instant;
import java.util.UUID;

@Mock
@RestClient
public class MockParkingServiceApi implements ParkingServicesApi {
    @Override
    public TicketInformation generateParkingTicketInfo(String licence) {
        return TicketInformation
                .builder()
                .ticketId(UUID.fromString("1aec4156-c19f-48ce-a303-c5c7ffb12572"))
                .parkingSpot(12321)
                .entryTime(Instant.now())
                .build();
    }

    @Override
    public TicketInformation generateParkingTicketInfo(Vehicle vehicle) {
        return TicketInformation
                .builder()
                .ticketId(UUID.fromString("1aec4156-c19f-48ce-a303-c5c7ffb12572"))
                .parkingSpot(12321)
                .entryTime(Instant.now())
                .build();
    }
}
