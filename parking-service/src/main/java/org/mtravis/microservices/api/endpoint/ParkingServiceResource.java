package org.mtravis.microservices.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mtravis.microservices.api.model.Ticket;
import org.mtravis.microservices.api.model.TicketDto;
import org.mtravis.microservices.api.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@Path("/api/parking")
public class ParkingServiceResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceResource.class);
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    /*
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"licenseNumber":"H2C 4Ch","type":"LARGE"}' \
  http://localhost:8702/api/parking
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateParkingTicket(String licenseNumber) {
        LOGGER.info("generateParkingTicket with license number:{}", licenseNumber);

        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .entryTime(Instant.now())
                .licenseNumber(licenseNumber)
                .parkingSpot(9876)
                .build();

        LOGGER.info("Created Ticket:{}", ticket.toString());
        return Response.status(Response.Status.ACCEPTED).entity(ticket).build();
    }
}
