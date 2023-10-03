package org.mtravis.microservices.api.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mtravis.microservices.SpotAllocationService;
import org.mtravis.microservices.model.ParkingSpot;
import org.mtravis.microservices.model.ParkingSpotDto;
import org.mtravis.microservices.persistence.ParkingSpotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Path("/api/spot/allocation")
public class SpotAllocationResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpotAllocationResource.class);

    @Inject
    SpotAllocationService spotAllocationService;



    /*
curl --header "Content-Type: application/json" \
--request POST \
--data '{"parking_type":"MEDIUM"}' \
http://localhost:8703/api/spot/allocation
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response allocateParkingSpot(ParkingSpotDto parkingSpotDto){
        LOGGER.info("fetchParkingSpot({})", parkingSpotDto.toString());

        // Generate a random long parking spot we don't yet have the business logic to
        // fetch from a database and find the "Closest" parking spot to be created!
        // Create dummy data and return.
        long parkingSpots =0;
        try {
            LOGGER.info("Attempting to fetch parking spot from database with vehicle type:'{}'", parkingSpotDto);
            ParkingSpot parkingSpot = spotAllocationService.assignParkingSpot(parkingSpotDto.spotType);
            if (parkingSpot == null) {
                // No parking spot available lets set dummy data to be returned
                parkingSpotDto.parkingSpot = 0;
                parkingSpotDto.spotType = ParkingSpotType.NA;
            } else {
                parkingSpotDto.parkingSpot = parkingSpot.getSpotNumber();
                parkingSpotDto.spotType = parkingSpot.getSpotType();
            }
            LOGGER.info("Updated parkingSpotDto:'{}'", parkingSpotDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("Return parking spot:{}", parkingSpotDto.toString());
        return Response.status(Response.Status.ACCEPTED).entity(parkingSpotDto).build();
    }
}
