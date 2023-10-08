package org.mtravis.microservices;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Produces;
import org.mtravis.microservices.model.ParkingSpot;
import org.mtravis.microservices.persistence.ParkingSpotEntity;
import org.mtravis.microservices.persistence.ParkingSpotMapper;
import org.mtravis.microservices.persistence.ParkingSpotRepository;
import org.mtravis.microservices.persistence.ParkingSpotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * This class represents the business logic for the spot allocation service
 */
@Produces
@ApplicationScoped
public class SpotAllocationService {
    @Inject
    ParkingSpotRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotAllocationService.class);
    ParkingSpotMapper mapper = new ParkingSpotMapper();
    @Transactional
    public ParkingSpot assignParkingSpot(ParkingSpotType vehicleType) throws IOException {
        LOGGER.info("Attempting to retrieve an unoccupied parking spot: {}", vehicleType);
        // we should have a cache layer to be created
        if(repository.find("occupied = ?1 and spotType = ?2",false, vehicleType).firstResult() == null){
            LOGGER.warn("Could not find a free parking spot with type:{}", vehicleType);
            // we should throw an exception not return null and catch it down stream.
            return null;
        }
        ParkingSpotEntity parkingSpotEntity =
                repository
                        .find("occupied = ?1 and spotType = ?2",false, vehicleType)
                        .firstResult();
        LOGGER.info("Found parking spot:{} attempting to update parking spot isOccupied = true", parkingSpotEntity.toString());
        boolean setOccupied = setParkingSpotOccupied(parkingSpotEntity.id, true);
        if(!setOccupied){
            LOGGER.warn("Could not update the parking spot: '{}' to occupied", parkingSpotEntity.id);
            return null;
        }
        return mapper.toDomain(parkingSpotEntity);
    }

    @Transactional
    public boolean setParkingSpotOccupied(UUID parkingSpotId, boolean isOccupied) {
        Map<String, Object> params = new HashMap<>();
        params.put("occupied", isOccupied);
        params.put("spot_id", parkingSpotId);
        int numberOfUpdates = repository.update("occupied = :occupied where id = :spot_id", params);
        if(numberOfUpdates == 0){
            LOGGER.warn("Failed to update isOccupied:'{}'", isOccupied);
            return false;
        }
        LOGGER.info("Successfully updated:'{}' records", numberOfUpdates);
        return true;
    }

    @Transactional
    public ParkingSpot unassignParkingSpot(UUID id) {

        return null;
    }
}
