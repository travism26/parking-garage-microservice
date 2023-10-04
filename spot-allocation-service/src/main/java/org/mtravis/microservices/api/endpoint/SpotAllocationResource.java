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
--data '{"vehicle_size":"MEDIUM"}' \
http://localhost:8703/api/spot/allocation
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response allocateParkingSpot(ParkingSpotDto parkingSpotDto){
        LOGGER.debug("fetchParkingSpot({})", parkingSpotDto.toString());
        try {
            LOGGER.debug("Attempting to fetch parking spot from database with vehicle type:'{}'", parkingSpotDto);
            ParkingSpot parkingSpot = spotAllocationService.assignParkingSpot(parkingSpotDto.spotType);
            if (parkingSpot == null) {
                // No parking spot available lets set dummy data to be returned handle downstream.
                // got to be a better approach ill research this.
                parkingSpotDto.parkingSpot = -1;
                parkingSpotDto.spotType = ParkingSpotType.NA;
            } else {
                parkingSpotDto.parkingSpot = parkingSpot.getSpotNumber();
                parkingSpotDto.spotType = parkingSpot.getSpotType();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Return parking spot:{}", parkingSpotDto);
        return Response.status(Response.Status.ACCEPTED).entity(parkingSpotDto).build();
    }
}
