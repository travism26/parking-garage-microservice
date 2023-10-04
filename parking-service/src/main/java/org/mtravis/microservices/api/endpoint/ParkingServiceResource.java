package org.mtravis.microservices.api.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mtravis.microservices.TicketService;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.model.TicketDto;
import org.mtravis.microservices.model.TicketDtoDomainMapper;
import org.mtravis.microservices.model.Vehicle;
import org.mtravis.microservices.resources.ParkingAllocationInformation;
import org.mtravis.microservices.resources.SpotAllocationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@Path("/api/parking")
public class ParkingServiceResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceResource.class);
    @Inject
    @RestClient
    SpotAllocationApi allocationApi;

    @Inject
    TicketDtoDomainMapper ticketMapper;
    @Inject
    TicketService ticketService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateParkingTicket(String licenseNumber) {
        LOGGER.info("generateParkingTicket with license number:{}", licenseNumber);

        // This will be used for persistence and NOT for end user
        // create a DTO for end user leave the model alone :)
        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .entryTime(Instant.now())
                .licenseNumber(licenseNumber)
                .parkingSpot(9876)
                .build();

        LOGGER.info("Created Ticket:{}", ticket.toString());
        return Response.status(Response.Status.ACCEPTED).entity(ticket).build();
    }

    /*
curl --header "Content-Type: application/json" \
--request POST \
--data '{"licenseNumber":"H2C 4Ch","type":"LARGE"}' \
http://localhost:8702/api/parking/v2
 */
    @POST
    @Path("/v2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateParkingTicket(Vehicle vehicle) {
        LOGGER.info("V2 generateParkingTicket vehicle info:{}", vehicle.toString());

        ParkingAllocationInformation allocationInformation = allocationApi.generateParkingTicketInfo(vehicle);
        LOGGER.info(allocationInformation.toString());

        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .entryTime(Instant.now())
                .licenseNumber(vehicle.getLicenseNumber())
                .parkingSpot(Math.abs(allocationInformation.getParkingSpot()))
                .build();
        // Map the ticket -> ticketDto that will be returned
        TicketDto ticketDto = ticketMapper.toDto(ticket);
        ticketDto.spotType = allocationInformation.getSpotType();
        // This is the part of persistence
        boolean persistBook = false;
        try {
            persistBook = ticketService.create(ticket);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(!persistBook){
            // book was not persisted.
            return Response.status(Response.Status.CONFLICT).build();
        }

        LOGGER.info("Created Ticket:{}", ticketDto);
        return Response.status(Response.Status.ACCEPTED).entity(ticketDto).build();
    }
}
