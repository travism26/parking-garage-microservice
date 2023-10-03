package org.mtravis.microservices;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Produces;
import org.mtravis.microservices.model.ParkingSpot;
import org.mtravis.microservices.model.Vehicle;
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

@Produces
@ApplicationScoped
public class SpotAllocationService {
    @Inject
    ParkingSpotRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotAllocationService.class);
    ParkingSpotMapper mapper = new ParkingSpotMapper();

    /*
        @Transactional
    public boolean create(Ticket ticket) throws JsonProcessingException {
        UUID id = ticket.getId();
        LOGGER.info("Attempting to persist a ticket with an ID '{}'", id);

        if (ticketRepository.find("id = ?1", id).firstResult() != null) {
            LOGGER.warn("Ticket already exists in the database, did not persist");
            return false;
        }
        TicketEntity ticketEntity = ticketMapper.toEntity(ticket);
        LOGGER.info("Saving to DB");
        ticketRepository.persist(ticketEntity);
        LOGGER.info("Successfully persisted book: {}", ticket);
        return true;

            @Transactional
    public boolean delete(UUID ticketId){
        TicketEntity ticketEntity =
                ticketRepository.find("id = ?1", ticketId).firstResult();
        if (ticketEntity == null){
            LOGGER.info("Ticket you attempted to delete does not exist");
            return false;
        }
        ticketRepository.delete("id = ?1", ticketId);
        return true;
    }
    }
     */
    @Transactional
    public ParkingSpot assignParkingSpot(ParkingSpotType vehicleType) throws IOException {
        // we get a request for a parking spot given a vehicleType: SMALL, MEDIUM, LARGE, XLARGE
        LOGGER.info("Attempting to retrieve an unoccupied parking spot: {}", vehicleType);
        Map<String, Object> params = new HashMap<>();
        params.put("spot_type", vehicleType.name());
        params.put("occupied", false);
        // we should have a cache layer to be created
        if(repository.find("occupied = ?1 and spotType = ?2",false, vehicleType).firstResult() == null){
            LOGGER.warn("Could not find spot_type");
            // we should throw an exception not return null and catch it down stream.
            return null;
        }
        LOGGER.info("Find ParkingSpot");
        ParkingSpotEntity parkingSpotEntity =
                repository
                        .find("occupied = ?1 and spotType = ?2",false, vehicleType)
                        .firstResult();
        LOGGER.info("Found parking spot:{}", parkingSpotEntity.toString());
        boolean setOccupied = setParkingSpotOccupied(parkingSpotEntity.id, true);
        if(!setOccupied){
            LOGGER.info("Could not update the parking spot: '{}' to occupied", parkingSpotEntity.id);
            return null;
        }
        return mapper.toDomain(parkingSpotEntity);
    }

    @Transactional
    public boolean setParkingSpotOccupied(UUID parkingSpotId, boolean isOccupied) {
        int numberOfUpdates = repository.update("occupied = ?1 where id = ?2", isOccupied, parkingSpotId);
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
