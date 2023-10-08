package org.mtravis.microservices.resources;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mtravis.microservices.model.ParkingSpotType;
import org.mtravis.microservices.model.Vehicle;

/**
 * This will mock our endpoint giving us reliable output from a mocked endpoint
 */
@Mock
@RestClient
public class MockSpotAllocationProxy implements SpotAllocationApi {
    @Override
    public ParkingAllocationInformation generateParkingTicketInfo(Vehicle vehicle) {
        return ParkingAllocationInformation.builder()
                .parkingSpot(123)
                .spotType(ParkingSpotType.MEDIUM)
                .build();
    }
}
