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
import org.mtravis.microservices.persistence.ParkingSpotEntity;
import org.mtravis.microservices.persistence.ParkingSpotRepository;
import org.mtravis.microservices.persistence.ParkingSpotType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class SpotAllocationServiceTest {

    static ParkingSpotRepository parkingSpotRepository = Mockito.mock(ParkingSpotRepository.class);

    @Inject
    SpotAllocationService allocationService;

    @BeforeAll
    static void setup(){
        QuarkusMock.installMockForType(parkingSpotRepository, ParkingSpotRepository.class);
    }

    @SneakyThrows
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_assignParkingSpot(){
        UUID id = UUID.randomUUID();
        ParkingSpotEntity parkingSpotEntity = new ParkingSpotEntity();
        parkingSpotEntity.id = id;
        parkingSpotEntity.spotNumber = 12345;
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        // Mock the repository find method to return the panacheQuery
        when(parkingSpotRepository.find(anyString(), (Object) any() )).thenReturn(panacheQuery);
        // mock the panacheQuery.firstResult() to return the parkingspotEntity
        when(panacheQuery.firstResult()).thenReturn(parkingSpotEntity);
        // mock the set parking spot occupied since this method updates the database I
        // need to mock that functionality as well.
        when(parkingSpotRepository.update(anyString(), anyMap())).thenReturn(1);
        // do the actual testing once we mocked everything
        Assertions.assertEquals(id, allocationService.assignParkingSpot(ParkingSpotType.LARGE).id);
        Assertions.assertEquals(12345, allocationService.assignParkingSpot(ParkingSpotType.LARGE).spotNumber);
    }

    @SneakyThrows
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void test_assignedParkingSpot_doesNotExist(){
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        when(parkingSpotRepository.find(anyString(), (Object) any())).thenReturn(panacheQuery);
        when(panacheQuery.firstResult()).thenReturn(null);
        Assertions.assertNull(allocationService.assignParkingSpot(ParkingSpotType.LARGE));
    }

    @Test
    void test_setParkingSpotOccupied(){
        when(parkingSpotRepository.update(anyString(), anyMap())).thenReturn(1);
        Assertions.assertTrue(allocationService.setParkingSpotOccupied(UUID.randomUUID(), true));
    }

    @Test
    void test_setParkingSpotOccupied_failed(){
        when(parkingSpotRepository.update(anyString(), anyMap())).thenReturn(0);
        Assertions.assertFalse(allocationService.setParkingSpotOccupied(UUID.randomUUID(), true));
    }
}
