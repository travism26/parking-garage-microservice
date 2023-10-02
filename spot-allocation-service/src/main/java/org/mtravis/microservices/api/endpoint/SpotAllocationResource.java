package org.mtravis.microservices.api.endpoint;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mtravis.microservices.model.ParkingSpotDto;
import org.mtravis.microservices.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.mtravis.microservices.model.ParkingSpot.spotType.XLARGE;

@Path("/api/spot/allocation")
public class SpotAllocationResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpotAllocationResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    // We need a POST endpoint that will get ticket information and find an available parking spot based off
    // vehicle information (SMALL, MED, LARGE, XLARGE)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response allocateParkingSpot(Vehicle vehicle){
        LOGGER.info("fetchParkingSpot({})", vehicle.toString());

        // Generate a random long parking spot we don't yet have the business logic to
        // fetch from a database and find the "Closest" parking spot to be created!
        // Create dummy data and return.
        long parkingSpot = new Random().nextLong();
        ParkingSpotDto parkingSpotDto = ParkingSpotDto.builder()
                .spotType(XLARGE)
                .parkingSpot(Math.abs(parkingSpot))
                .build();

        LOGGER.info("Return parkingspot:{}", parkingSpotDto.toString());
        return Response.status(Response.Status.ACCEPTED).entity(parkingSpotDto).build();
    }
}
