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

        // This make an API call to the allocation Service that's responsible for assigning a ticket a parking spot
        ParkingAllocationInformation allocationInformation = allocationApi.generateParkingTicketInfo(vehicle);
        LOGGER.info(allocationInformation.toString());

        // Make a call to the ticketService to generate a ticket
        Ticket ticket = ticketService.generateTicket(vehicle.getLicenseNumber(), allocationInformation.getParkingSpot());
        // DTO Object return value
        TicketDto ticketDto = ticketMapper.toDto(ticket);
        ticketDto.spotType = allocationInformation.getSpotType();
        boolean persistBook = false;
        try {
            // Attempt to persist the book into the database
            persistBook = ticketService.create(ticket);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(!persistBook){
            return Response.status(Response.Status.CONFLICT).build();
        }

        LOGGER.info("Created Ticket:{}", ticketDto);
        return Response.status(Response.Status.ACCEPTED).entity(ticketDto).build();
    }
}
