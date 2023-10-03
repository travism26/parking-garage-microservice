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
import org.mtravis.microservices.model.Vehicle;
import org.mtravis.microservices.persistence.ParkingSpotEntity;
import org.mtravis.microservices.persistence.ParkingSpotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

import static org.mtravis.microservices.persistence.ParkingSpotType.XLARGE;

@Path("/api/spot/allocation")
public class SpotAllocationResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpotAllocationResource.class);

    @Inject
    SpotAllocationService spotAllocationService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    // We need a POST endpoint that will get ticket information and find an available parking spot based off
    // vehicle information (SMALL, MED, LARGE, XLARGE)

    
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
            parkingSpotDto.parkingSpot = parkingSpot.getSpotNumber();
            parkingSpotDto.spotType = parkingSpot.getSpotType();
            LOGGER.info("Updated parkingSpotDto:'{}'", parkingSpotDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("Return parkingspot:{}", parkingSpotDto.toString());
        return Response.status(Response.Status.ACCEPTED).entity(parkingSpotDto).build();
    }
}
