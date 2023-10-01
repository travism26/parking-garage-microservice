package org.mtravis.microservices.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mapstruct.Mapper;
import org.mtravis.microservices.model.Ticket;

import java.io.IOException;

/**
 * This class is responsible for mapping the entity <--> domain this will be used
 * In the ticket service to save a ticket to the database, and vice verse map a
 * TicketEntity back to a ticket object.
 */

@Mapper(componentModel = "jakarta")
public class TicketMapper implements DomainEntityMapper<Ticket, TicketEntity> {
    @Override
    public TicketEntity toEntity(Ticket domain) throws JsonProcessingException {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.id = domain.getId();
        ticketEntity.parkingSpot = domain.getParkingSpot();
        ticketEntity.licenseNumber = domain.getLicenseNumber();
        ticketEntity.entryTime = domain.getEntryTime();
        ticketEntity.exitTime = domain.getExitTime();
        return ticketEntity;
    }

    @Override
    public Ticket toDomain(TicketEntity entity) throws IOException {
        return Ticket.builder()
                .id(entity.id)
                .parkingSpot(entity.parkingSpot)
                .licenseNumber(entity.licenseNumber)
                .entryTime(entity.entryTime)
                .exitTime(entity.exitTime)
                .build();
    }
}
