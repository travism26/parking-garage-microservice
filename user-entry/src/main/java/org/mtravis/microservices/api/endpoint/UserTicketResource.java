package org.mtravis.microservices.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.model.UserEntryDto;
import org.mtravis.microservices.resources.ParkingServicesApi;
import org.mtravis.microservices.resources.TicketInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@Path("/api/ticket")
@Tag(name = "Create Parking Ticket Endpoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserTicketResource {

    @Inject
    @RestClient
    ParkingServicesApi parkingServicesApi;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTicketResource.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /* curl call:
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"vehicle":{"license_number":"1337","vehicle_size":"MEDIUM"}}' \
  http://localhost:8701/api/ticket
     */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Generates a ticket")
    @Retry(maxRetries = 1, delay = 2000)
    @Fallback(fallbackMethod = "fallbackOnTicketCreate")
    @Timed(value = "timeCreateTicket",description = "How long it takes to generate a ticket")
    public Response createTicket(UserEntryDto userEntryDto) {
        LOGGER.debug("Executing createTicket('{}')", userEntryDto);
        if (userEntryDto.id == null){
            userEntryDto.generateId();
        }
        TicketInformation ticketInformation = parkingServicesApi.generateParkingTicketInfo(userEntryDto.vehicle);
        LOGGER.debug("updatedTicketService:{}", ticketInformation.toString());
        Ticket ticket = Ticket.builder()
                .id(ticketInformation.ticketId)
                .parkingSpot(ticketInformation.parkingSpot)
                .licenseNumber(userEntryDto.getVehicle().getLicenseNumber())
                .entryTime(ticketInformation.entryTime)
                .build();
        LOGGER.info("Ticket created:{}", ticket);
        return Response.status(Response.Status.ACCEPTED)
                .entity(ticket)
                .build();
    }

    public Response fallbackOnTicketCreate(UserEntryDto userEntryDto) {
        LOGGER.warn("Executing fallback createTicket('{}')", userEntryDto);
        if (userEntryDto.id == null){
            userEntryDto.generateId();
        }
        LOGGER.info("Generating fake parking data.");
        userEntryDto.ticketId = UUID.randomUUID();
        userEntryDto.parkingSpot = 1337;
        String licenseNumber = userEntryDto.vehicle.getLicenseNumber() == null ? "EJH123" : userEntryDto.getVehicle().getLicenseNumber();
        Ticket ticket = Ticket.builder()
                .id(userEntryDto.getTicketId())
                .parkingSpot(userEntryDto.getParkingSpot())
                .licenseNumber(licenseNumber)
                .entryTime(Instant.now())
                .build();
        LOGGER.info("Ticket created:{}", ticket);
        return Response.status(Response.Status.ACCEPTED)
                .entity(ticket)
                .build();
    }
}
